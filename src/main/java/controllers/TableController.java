package controllers;

import models.Table;
import org.pmw.tinylog.Logger;

import static controllers.GameController.*;
import static models.Table.*;

/**
 *
 * Háttér mátriy ami alapján leképezi a View-t.
 */
public class TableController {

    /**
     * Inicializálja a mátrixot és felölti Neutrális mező
     * azonosítóval.
     *
     * @param table {@code table} table objektum, ami végig van
     * ráncigálva az egész kódon...
     */
    public void create(Table table){
        int tmp [][] = new int[SIZE][SIZE];
        for(int i =0; i < SIZE; i++){
            for (int j =0; j < SIZE; j++){
                tmp[i][j] = NEUTRAL_ID;
            }
        }
        table.setTable(tmp);
        Logger.info("Matrix filled by {}", NEUTRAL_ID);
    }

    /**
     * Elhelyezi a huszért körnek megfelelően.
     *
     * @param i {@code i} sor index.
     * @param j {@code j} index.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public void place(int i, int j, Table table){
        int tmp [][] = table.getTable();
        if(Turn%2 == 0){
            if(tmp[i][j] == NEUTRAL_ID){
                tmp[i][j] = P1_KNIGHT_ID;
                Logger.info("First player placed his/her knight on the field.");
            }
        }
        else {
            if(tmp[i][j] == NEUTRAL_ID){
                tmp[i][j] = P2_KNIGHT_ID;
                Logger.info("Second player placed his/her knight on the field.");
            }
        }
        table.setTable(tmp);
    }

    /**
     * Egy pozicóból kiszámolja a lehetséges lépés helyeket.
     * Ha nincs akkor a játékos veszít.
     *
     * @param i {@code i} sor index.
     * @param j {@code j} oszlop index.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public void highlight(int i, int j, Table table){
        GameController gameController = new GameController();

        int[][] tmp = table.getTable();
        int counter = 0;
        int[] x = {1, 1, -1, -1, 2, 2, -2, -2};
        int[] y = {2, -2, 2, -2, 1, -1, 1, -1};

        for(int c = 0; c < x.length; c++){
            try {
                if(tmp[i + x[c]][j + y[c]] == NEUTRAL_ID){
                    tmp[i + x[c]][j + y[c]] = HIGLIGHT_ID;
                    counter++;
                }
            } catch (ArrayIndexOutOfBoundsException e){
            }
        }
        table.setTable(tmp);
        if(counter == 0){
            Logger.info("One of the player couldn't move anymore!");
            Turn++;
            gameController.Win(table);
        }
    }

    /**
     * Azt vizsgálja, hogy
     * egy mező Highlighted azonosítóval van-e ellátva.
     *
     * @param i {@code i} sor inex.
     * @param j {@code j} oszlop index.
     * @return Ha lpéhető mező True, különben False.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public boolean isHighlighted(int i, int j, Table table){
        int[][] tmp = table.getTable();
        if(tmp[i][j] == HIGLIGHT_ID){
            return true;
        }
        else return false;
    }

    /**
     * Huszár áll-e azon a mezőn.
     *
     * @param i {@code i} sor index.
     * @param j {@code j} oszlop index.
     * @return ha huszár áll azon a mezőn True-t add vissza,
     * különben False-t.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public boolean isKnight(int i, int j, Table table){
        int[][] tmp = table.getTable();
        if(tmp[i][j] == P1_KNIGHT_ID || tmp[i][j] == P2_KNIGHT_ID)
            return true;
        else return false;
    }

    /**
     * Elmozdítja huszárt az eredi mezőjéről és áthelyi a
     * lekattintott mezőre.
     *
     * @param old_i {@code old_i} régi huszár sor index.
     * @param old_j {@code old_j} régi huszár oszlop index.
     * @param new_i {@code new_i} új huszár sor index.
     * @param new_j {@code new_j} új huszár oszlop index.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public void move(int old_i, int old_j, int new_i, int new_j, Table table){
        GameController gameController = new GameController();
        int[][] tmp = table.getTable();
        if(Turn%2==0){
            tmp[old_i][old_j] = P1_FIELD_ID;
            tmp[new_i][new_j] = P1_KNIGHT_ID;
            Logger.info("First player moved.");
        }
        else {
            tmp[old_i][old_j] = P2_FIELD_ID;
            tmp[new_i][new_j] = P2_KNIGHT_ID;
            Logger.info("Second player moved.");
        }
        table.setTable(tmp);

        if(gameController.isWin(table)){
            gameController.Win(table);
        }
        if(gameController.isDraw(table)){
            gameController.Draw(table);
        }
        Turn++;
        rehighlighted(table);
    }

    /**
     * Ha mozgítás bekövetkezett, akkor a megjelölt mezőket neutrálisra állítja,
     * ha az highlited azonosítóval volt ellátva.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    private void rehighlighted(Table table){
        int[][] tmp = table.getTable();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(tmp[i][j] == HIGLIGHT_ID){
                    tmp[i][j] = NEUTRAL_ID;
                }
            }
        }
        table.setTable(tmp);
        Logger.info("Turn end. All highlighted field become free.");
    }

}
