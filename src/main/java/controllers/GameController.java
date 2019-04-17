package controllers;

import views.GameView;
import views.PlayerView;
import views.TableView;

import static controllers.PlayerController.P1;
import static controllers.PlayerController.P2;
import static controllers.TableController.*;
import static models.Table.*;

/**
 * A játék végét értelmező eljárások. Két véget ismer el a
 * győzelmet és a döntetlent. A győzelem a játékszabályoknak megfelelően.
 * A döntetlen, akkor ha minden mező elfoglalódik.
 */
public class GameController {

    /**
     * Körök számának számolása. Páros körök az első
     * játékosé a páratlan a második játékosé.
     */
    public static int Turn = 0;

    /**
     * Győzelem, akkor ha egy sorban egymás melett 5 azonos szín van,
     * ha 5 szín egymás alatt van vagy 5 szín átlósan helyezkedik el.
     *
     * @return Ha valamelyik feltétel teljesül True-t add vissza, különben False-t.
     */
    public static boolean isWin(){
        if(onRow() || onDiagonal() || onCol()){
            return true;
        }
        else return false;
    }

    /**
     * A győzelem után a győztes játékos győzelmeinek számát 1-el növeli, mind
     * a játékos adatainál és mind a játék session-él. Majd reset-i a pályát és
     * a körök számát.
     * FONTOS! győzelem az is, ha a másik beszórítja magát.
     */
    public static void Win(){
        GameDataController gameDataController = new GameDataController();
        PlayerController playerController = new PlayerController();
        GameView gameView = new GameView();
        if(Turn%2 == 0){
            GameDataController.Session.setPlayer1_wins(
                    GameDataController.Session.getPlayer1_wins() + 1
            );
            P1.setWins(P1.getWins() + 1);
            playerController.Update(P1);
            gameView.Win(P1);
        }else {
            GameDataController.Session.setPlayer2_wins(
                    GameDataController.Session.getPlayer2_wins() + 1
            );
            P2.setWins(P2.getWins() + 1);
            playerController.Update(P2);
            gameView.Win(P2);
        }
        gameDataController.Update(GameDataController.Session);
        TableController.createController();
        TableView.refresh();
        PlayerView playerView = new PlayerView();
        playerView.Update();
        StandingsController standingsController = new StandingsController();
        standingsController.Refresh();
    }

    /**
     * A döntetlen helyezetének felismerése.
     *
     * @return Ha döntetlen True, különben False.
     */
    public static boolean isDraw(){
        int counter = 0;
        for(int i = 0; i < SIZE; i++){
            for(int j  =0; j < SIZE; j++){
                if(Table[i][j] == NEUTRAL_ID)
                    counter++;
            }
        }
        if(counter == 1){
            return true;
        }
        else if (counter > 1){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Ha már csak egy szabad mező marad az egyik játékosnak
     * (nem tudd lépni egyik sem többet) döntetlen hírdett.
     */
    public static void Draw(){
        TableController.createController();
        TableView.refresh();
        PlayerView playerView = new PlayerView();
        playerView.Update();
        GameView gameView = new GameView();
        gameView.Draw();
    }

    /**
     * Egy sor ellenőrzésének algoritmusa. Minden mezőhöz amelyik nem default szín
     * megnézi, hogy még 4 követi. (Sajnos végig megy a mátrixon).
     *
     * @param tmp elem érték aktuálisan.
     * @param pkid játékos huszár azonosítója.
     * @param pfid a játékos terület azonosítója.
     * @param i az aktuálisan ellenőrzőtt elem sor indexe.
     * @param j az aktuálisan ellenőrzőtt elem oszlop indexe.
     * @return Ha feltételek teljesülnek True-t add vissza.
     */
    public static boolean onRowHelper(int tmp, int pkid, int pfid, int i, int j) {
        int counter = 0;
        if(tmp == pkid|| tmp == pfid){
            for(int c = 0; c < SIZE / 2; c++){
                try {
                    if(Table[i][j + c] == pkid
                            || Table[i][j + c] == pfid){
                        counter++;
                    } else break;
                    if(counter == SIZE / 2) return true;
                } catch (ArrayIndexOutOfBoundsException e){
                    break;
                }

            }
        }
        return false;
    }

    /**
     * Végig haladva a mátrixon miden ellemre egyesével (#sadface#) ellőrzni
     * a fetételeket. Ez a soron ellenőrzni.
     *
     * @return Ha kigyült az 5 szín True-t add vissza, különben False.
     */
    public static boolean onRow(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                int tmp = Table[i][j];
                if(onRowHelper(tmp, P1_KNIGHT_ID, P1_FIELD_ID, i, j))
                    return true;
                if(onRowHelper(tmp, P2_KNIGHT_ID, P2_FIELD_ID, i, j))
                    return true;
            }
        }
        return false;
    }

    /**
     * Egy oszlop ellenőrzésének algoritmusa. Minden mezőhöz amelyik nem default szín
     * megnézi, hogy még 4 követi. (Sajnos végig megy a mátrixon).
     *
     * @param tmp elem érték aktuálisan.
     * @param pkid játékos huszár azonosítója.
     * @param pfid a játékos terület azonosítója.
     * @param i az aktuálisan ellenőrzőtt elem sor indexe.
     * @param j az aktuálisan ellenőrzőtt elem oszlop indexe.
     * @return Ha feltételek teljesülnek True-t add vissza.
     */
    public static boolean onColHelper(int tmp, int pkid, int pfid, int i, int j) {
        int counter = 0;
        if(tmp == pkid|| tmp == pfid){
            for(int c = 0; c < SIZE / 2; c++){
                if(Table[i + c][j] == pkid
                        || Table[i + c][j] == pfid){
                    counter++;
                } else break;
                if(counter == SIZE / 2) return true;
            }
        }
        return false;
    }

    /**
     * Végig haladva a mátrixon miden ellemre egyesével (#sadface#) ellőrzni
     * a fetételeket. Ez az oszlopon ellenőrzni.
     *
     * @return Ha kigyült az 5 szín True-t add vissza, különben False.
     */
    public static boolean onCol(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                int tmp = Table[i][j];
                if(onColHelper(tmp, P1_KNIGHT_ID, P1_FIELD_ID, i, j))
                    return true;
                if(onColHelper(tmp, P2_KNIGHT_ID, P2_FIELD_ID, i, j))
                    return true;
            }
        }
        return false;
    }

    /**
     * Végig haladva a mátrixon miden ellemre egyesével (#sadface#) ellőrzni
     * a fetételeket. Ez az átlókban ellenőrzni.
     *
     * @return Ha kigyült az 5 szín True-t add vissza, különben False.
     */
    public static boolean onDiagonal(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){

                if(onDiagonalHelper(i, j, P1_FIELD_ID, P1_KNIGHT_ID))
                    return true;
                if(onDiagonalHelper(i, j, P2_FIELD_ID, P2_KNIGHT_ID))
                    return true;
            }
        }
        return false;
    }

    /**
     * Az átlóban ellenőrzésének algoritmusa. Minden mezőhöz amelyik nem default szín
     * megnézi, hogy még 4 követi. (Sajnos végig megy a mátrixon).
     *
     * @param i az aktuálisan ellenőrzőtt elem sor indexe.
     * @param j az aktuálisan ellenőrzőtt elem oszlop indexe.
     * @param p2FieldId játékos terület azonosítója.
     * @param p2KnightId a játékos huszár azonosítója.
     * @return Ha feltételek teljesülnek True-t add vissza.
     */
    private static boolean onDiagonalHelper(int i, int j, int p2FieldId, int p2KnightId) {
        int[] counters = { 0, 0, 0, 0};
        if(Table[i][j] == p2FieldId
                || Table[i][j] == p2KnightId){
            for(int c = 0; c < SIZE; c++){
                try {
                    if(Table[i + c][j + c] == p2KnightId ||
                            Table[i + c][j + c] == p2FieldId){
                        counters[0]++;
                    }
                    if(Table[i - c][j + c] == p2KnightId ||
                            Table[i - c][j + c] == p2FieldId){
                        counters[1]++;
                    }
                    if(Table[i + c][j - c] == p2KnightId ||
                            Table[i + c][j - c] == p2FieldId){
                        counters[2]++;
                    }
                    if(Table[i - c][j - c] == p2KnightId ||
                            Table[i - c][j - c] == p2FieldId){
                        counters[3]++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                for (int co: counters) {
                    if(co == 5){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
