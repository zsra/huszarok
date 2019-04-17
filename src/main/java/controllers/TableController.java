package controllers;

import static controllers.GameController.*;
import static models.Table.*;

/**
 *
 * Háttér mátriy ami alapján leképezi a View-t.
 */
public class TableController {

    /**
     * Háttér mátrix.
     */
    public static int[][] Table = new int[SIZE][SIZE];

    /**
     * Inicializálja a mátrixot és felölti Neutrális mező
     * azonosítóval.
     */
    public static void createController(){
        for(int i =0; i < SIZE; i++){
            for (int j =0; j < SIZE; j++){
                Table[i][j] = NEUTRAL_ID;
            }
        }
    }

    /**
     * Elhelyezi a huszért körnek megfelelően.
     *
     * @param i sor index.
     * @param j oszlop index.
     */
    public static void place(int i, int j){
        if(Turn%2 == 0){
            if(Table[i][j] == NEUTRAL_ID){
                Table[i][j] = P1_KNIGHT_ID;
            }
        }
        else {
            if(Table[i][j] == NEUTRAL_ID){
                Table[i][j] = P2_KNIGHT_ID;
            }
        }
    }

    /**
     * Egy pozicóból kiszámolja a lehetséges lépés helyeket.
     * Ha nincs akkor a játékos veszít.
     *
     * @param i sor index.
     * @param j oszlop index.
     */
    public static void highlight(int i, int j){
        int counter = 0;
        int[] x = {1, 1, -1, -1, 2, 2, -2, -2};
        int[] y = {2, -2, 2, -2, 1, -1, 1, -1};

        for(int c = 0; c < x.length; c++){
            try {
                if(Table[i + x[c]][j + y[c]] == NEUTRAL_ID){
                    Table[i + x[c]][j + y[c]] = HIGLIGHT_ID;
                    counter++;
                }
            } catch (ArrayIndexOutOfBoundsException e){
            }
        }
        if(counter == 0){
            Turn++;
            Win();
        }
    }

    /**
     * Azt vizsgálja, hogy
     * egy mező Highlighted azonosítóval van-e ellátva.
     *
     * @param i sor inex.
     * @param j oszlop index.
     * @return Ha lpéhető mező True, különben False.
     */
    public static boolean isHighlighted(int i, int j){
        if(Table[i][j] == HIGLIGHT_ID) return true;
        else return false;
    }

    /**
     * Huszár áll-e azon a mezőn.
     *
     * @param i sor index.
     * @param j oszlop index.
     * @return ha huszár áll azon a mezőn True-t add vissza,
     * különben False-t.
     */
    public static boolean isKnight(int i, int j){
        if(Table[i][j] == P1_KNIGHT_ID
                || Table[i][j] == P2_KNIGHT_ID)
            return true;
        else return false;
    }

    /**
     * Elmozdítja huszárt az eredi mezőjéről és áthelyi a
     * lekattintott mezőre.
     *
     * @param old_i régi huszár sor index.
     * @param old_j régi huszár oszlop index.
     * @param new_i új huszár sor index.
     * @param new_j új huszár oszlop index.
     */
    public static void move(int old_i, int old_j, int new_i, int new_j){
        if(Turn%2==0){
            Table[old_i][old_j] = P1_FIELD_ID;
            Table[new_i][new_j] = P1_KNIGHT_ID;
        }
        else {
            Table[old_i][old_j] = P2_FIELD_ID;
            Table[new_i][new_j] = P2_KNIGHT_ID;
        }
        if(isWin()){
            Win();
        }
        if(isDraw()){
            Draw();
        }
        Turn++;
        rehighlighted();
    }

    /**
     * Ha mozgítás bekövetkezett, akkor a megjelölt mezőket neutrálisra állítja,
     * ha az highlited azonosítóval volt ellátva.
     */
    private static void rehighlighted(){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(Table[i][j] == HIGLIGHT_ID){
                    Table[i][j] = NEUTRAL_ID;
                }
            }
        }
    }

}
