package controllers;

import models.Table;
import org.pmw.tinylog.Logger;
import views.GameView;
import views.PlayerView;
import views.TableView;

import static controllers.GameDataController.*;
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
     * Ha valaki győzőtt, akkor az {@link #Win(Table)} metódus fut le.
     *
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha valamelyik feltétel teljesül True-t add vissza, különben False-t.
     */
    public boolean isWin(Table table){
        if(onRow(table) || onDiagonal(table) || onCol(table)){
            Logger.info("Somebody wins!");
            return true;
        }
        else return false;
    }

    /**
     * A győzelem után a győztes játékos győzelmeinek számát 1-el növeli, mind
     * a játékos adatainál és mind a játék session-él. Majd reset-i a pályát és
     * a körök számát.
     * FONTOS! győzelem az is, ha a másik beszórítja magát.
     *
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public void Win(Table table){

        GameDataController gameDataController = new GameDataController();
        PlayerController playerController = new PlayerController();
        TableController tableController = new TableController();
        StandingsController standingsController = new StandingsController();

        PlayerView playerView = new PlayerView();
        GameView gameView = new GameView();

        if(Turn%2 == 0){
            int p1w = Session.getPlayer1_wins();
            Session.setPlayer1_wins(p1w + 1);
            P1.setWins(P1.getWins() + 1);
            playerController.Update(P1);
            gameView.Win(P1);
            Logger.info("{} updated after the match.", P1.getName());
        } else {
            int p2w = Session.getPlayer2_wins();
            Session.setPlayer2_wins(p2w + 1);
            P2.setWins(P2.getWins() + 1);
            playerController.Update(P2);
            gameView.Win(P2);
            Logger.info("{} updated after the match.", P2.getName());
        }

        gameDataController.Update(Session);
        tableController.create(table);

        TableView.refresh(table);
        Logger.info("Table refreshed.");

        playerView.Update();
        Logger.info("PlayerView refreshed.");

        standingsController.Refresh();
        Logger.info("Result refreshed.");

        Turn = 0;
    }

    /**
     * A döntetlen helyezetének felismerése. Ha döntetlen, akkor
     * {@link #Draw(Table)} metódus futt le.
     *
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha döntetlen True, különben False.
     */
    public boolean isDraw(Table table){
        int[][] tmp = table.getTable();
        int counter = 0;
        for(int i = 0; i < SIZE; i++){
            for(int j  =0; j < SIZE; j++){
                if(tmp[i][j] == NEUTRAL_ID)
                    counter++;
            }
        }
        if(counter == 0) {
            Logger.info("Draw!");
            return true;
        }
        return false;
    }

    /**
     * Ha már csak egy szabad mező marad az egyik játékosnak
     * (nem tudd lépni egyik sem többet) döntetlen hírdett.
     *
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public void Draw(Table table){
        TableController tableController = new TableController();

        PlayerView playerView = new PlayerView();
        GameView gameView = new GameView();

        tableController.create(table);

        TableView.refresh(table);
        Logger.info("Tableview updated.!");

        playerView.Update();
        Logger.info("Playerview updated!");

        gameView.Draw();
        Turn = 0;
    }

    /**
     * Egy sor ellenőrzésének algoritmusa. Minden mezőhöz amelyik nem default szín
     * megnézi, hogy még 4 követi. (Sajnos végig megy a mátrixon).
     * {@link #onRow(Table)} segédmetódusa.
     *
     * @param tmp <code>tmp</code> elem érték aktuálisan.
     * @param pkid <code>pkid</code> játékos huszár azonosítója.
     * @param pfid <code>pfid</code> a játékos terület azonosítója.
     * @param i <code>i</code> az aktuálisan ellenőrzőtt elem sor indexe.
     * @param j <code>j</code> az aktuálisan ellenőrzőtt elem oszlop indexe.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha feltételek teljesülnek True-t add vissza.
     */
    private boolean onRowHelper(int tmp, int pkid,
                                       int pfid, int i, int j, Table table) {
        int[][] tmp_m = table.getTable();
        int counter = 0;
        if(tmp == pkid|| tmp == pfid){
            for(int c = 0; c < SIZE / 2; c++){
                try {
                    if(tmp_m[i][j + c] == pkid
                            || tmp_m[i][j + c] == pfid){
                        counter++;
                    } else break;
                    if(counter == SIZE / 2){
                        Logger.info("5 same color in one row.");
                        return true;
                    }
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
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha kigyült az 5 szín True-t add vissza, különben False.
     */
    public boolean onRow(Table table){
        int[][] tmp_m = table.getTable();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                int tmp = tmp_m[i][j];
                if(onRowHelper(tmp, P1_KNIGHT_ID, P1_FIELD_ID, i, j, table))
                    return true;
                if(onRowHelper(tmp, P2_KNIGHT_ID, P2_FIELD_ID, i, j, table))
                    return true;
            }
        }
        return false;
    }

    /**
     * Egy oszlop ellenőrzésének algoritmusa. Minden mezőhöz amelyik nem default szín
     * megnézi, hogy még 4 követi. (Sajnos végig megy a mátrixon).
     * {@link #onCol(Table)} segédmetódusa.
     *
     * @param tmp <code>tmp</code> elem érték aktuálisan.
     * @param pkid <code>pkid</code> játékos huszár azonosítója.
     * @param pfid <code>pfid</code> a játékos terület azonosítója.
     * @param i <code>i</code> az aktuálisan ellenőrzőtt elem sor indexe.
     * @param j <code>j</code> az aktuálisan ellenőrzőtt elem oszlop indexe.
     *
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha feltételek teljesülnek True-t add vissza.
     */
    private boolean onColHelper(int tmp, int pkid, int pfid,
                                       int i, int j, Table table) {
        int[][] tmp_m = table.getTable();
        int counter = 0;
        if(tmp == pkid|| tmp == pfid){
            for(int c = 0; c < SIZE / 2; c++){

                try {
                    if(tmp_m[i + c][j] == pkid
                            || tmp_m[i + c][j] == pfid){
                        counter++;
                    } else break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            if(counter == SIZE / 2) {
                Logger.info("5 same color in one column.");
                return true;
            }
        }
        return false;
    }

    /**
     * Végig haladva a mátrixon miden ellemre egyesével (#sadface#) ellőrzni
     * a fetételeket. Ez az oszlopon ellenőrzni.
     *
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha kigyült az 5 szín True-t add vissza, különben False.
     */
    public boolean onCol(Table table){
        int[][] tmp_m = table.getTable();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                int tmp = tmp_m[i][j];
                if(onColHelper(tmp, P1_KNIGHT_ID, P1_FIELD_ID, i, j, table))
                    return true;
                if(onColHelper(tmp, P2_KNIGHT_ID, P2_FIELD_ID, i, j, table))
                    return true;
            }
        }
        return false;
    }

    /**
     * Végig haladva a mátrixon miden ellemre egyesével (#sadface#) ellőrzni
     * a fetételeket. Ez az átlókban ellenőrzni.
     *
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha kigyült az 5 szín True-t add vissza, különben False.
     */
    public boolean onDiagonal(Table table){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){

                if(onDiagonalHelper(i, j, P1_FIELD_ID, P1_KNIGHT_ID, table)){
                    return true;
                }
                if(onDiagonalHelper(i, j, P2_FIELD_ID, P2_KNIGHT_ID, table)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Az átlóban ellenőrzésének algoritmusa. Minden mezőhöz amelyik nem default szín
     * megnézi, hogy még 4 követi. (Sajnos végig megy a mátrixon).
     * {@link #onDiagonal(Table)} segédmetódusa.
     *
     * @param i <code>i</code> az aktuálisan ellenőrzőtt elem sor indexe.
     * @param j <code>j</code> az aktuálisan ellenőrzőtt elem oszlop indexe.
     * @param p2FieldId <code>p2FieldId</code> játékos terület azonosítója.
     * @param p2KnightId <code>p2knightId</code> a játékos huszár azonosítója.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     * @return Ha feltételek teljesülnek True-t add vissza.
     */
    private boolean onDiagonalHelper(int i, int j, int p2FieldId,
                                            int p2KnightId, Table table) {
        int[][] tmp_m = table.getTable();
        int[] counters = { 0, 0, 0, 0};
        if(tmp_m[i][j] == p2FieldId
                || tmp_m[i][j] == p2KnightId){
            for(int c = 0; c < SIZE; c++){
                try {
                    if(tmp_m[i + c][j + c] == p2KnightId ||
                            tmp_m[i + c][j + c] == p2FieldId){
                        counters[0]++;
                    }
                    if(tmp_m[i - c][j + c] == p2KnightId ||
                            tmp_m[i - c][j + c] == p2FieldId){
                        counters[1]++;
                    }
                    if(tmp_m[i + c][j - c] == p2KnightId ||
                            tmp_m[i + c][j - c] == p2FieldId){
                        counters[2]++;
                    }
                    if(tmp_m[i - c][j - c] == p2KnightId ||
                            tmp_m[i - c][j - c] == p2FieldId){
                        counters[3]++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                for (int co: counters) {
                    if(co == 5){
                        Logger.info("5 same color in one diagonal.");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
