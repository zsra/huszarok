package controllers;

import models.Table;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static models.Table.*;
import static org.junit.jupiter.api.Assertions.*;

class TableControllerTest {

    Table table = new Table();
    TableController tableController = new TableController();

    @Test
    void create() {
        int[][] testTable = new int[SIZE][SIZE];
        for(int[] row : testTable){
            Arrays.fill(row, NEUTRAL_ID);
        }
        tableController.create(table);

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                assertEquals(testTable[i][j], table.getTable()[i][j]);
            }
        }
    }

    @Test
    void place() {
        int i = 3;
        int j = 6;
        int Turn_tmp = GameController.Turn;

        GameController.Turn = 0;
        tableController.place(i, j, table);
        assertEquals(table.getTable()[i][j], P1_KNIGHT_ID);

        int k = 1;
        int l = 5;
        GameController.Turn++;

        tableController.place(k, l, table);
        assertEquals(table.getTable()[k][l], P2_KNIGHT_ID);

        GameController.Turn = Turn_tmp;
    }

    @Test
    void highlight() {

        tableController.create(table);
        int[] x = {1, 1, -1, -1, 2, 2, -2, -2};
        int[] y = {2, -2, 2, -2, 1, -1, 1, -1};

        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                tableController.highlight(i, j, table);
                for(int c = 0; c < x.length; c++){
                    try {
                        assertEquals(table.getTable()[i + x[c]][j + y[c]], HIGLIGHT_ID);
                    } catch (ArrayIndexOutOfBoundsException e){ }
                }
                tableController.rehighlighted(table);
            }
        }

    }

    @Test
    void isHighlighted() {
        int i = 5;
        int j = 5;

        table.getTable()[i][j] = HIGLIGHT_ID;
        assertEquals(table.getTable()[i][j], HIGLIGHT_ID);
        tableController.rehighlighted(table);
    }

    @Test
    void rehighlighted() {
        int[] indexes = { 1, 4 ,5, 2, 3};
        for(int i = 0; i < indexes.length; i++){
            table.getTable()[i][indexes.length - i] = HIGLIGHT_ID;
        }

        tableController.rehighlighted(table);
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                assertEquals(table.getTable()[i][j], NEUTRAL_ID);
            }
        }
    }

    @Test
    void isKnight() {
        int i = 2;
        int j = 4;
        int Turn_tmp = GameController.Turn;

        GameController.Turn = 0;
        tableController.place(i, j, table);
        assertEquals(tableController.isKnight(i, j, table), true);

        GameController.Turn++;
        i = 6;
        j = 7;
        tableController.place(i, j, table);
        assertEquals(tableController.isKnight(i, j, table), true);

        i = 2;
        j = 1;
        assertEquals(tableController.isKnight(i, j, table), false);

        tableController.create(table);
        GameController.Turn = Turn_tmp;
    }

    @Test
    void move() {

        int old_i = 4;
        int old_j = 8;
        int valid_new_i = 5;
        int valid_new_j = 6;
        int Turn_tmp = GameController.Turn;

        GameController.Turn = 0;
        tableController.place(old_i, old_j, table);
        tableController.highlight(old_i, old_j, table);
        tableController.move(old_i, old_j, valid_new_i, valid_new_j, table);

        assertEquals(table.getTable()[old_i][old_j], P1_FIELD_ID);
        assertEquals(table.getTable()[valid_new_i][valid_new_j], P1_KNIGHT_ID);

        tableController.create(table);

        GameController.Turn = 1;
        tableController.place(old_i, old_j, table);
        tableController.highlight(old_i, old_j, table);
        tableController.move(old_i, old_j, valid_new_i, valid_new_j, table);

        assertEquals(table.getTable()[old_i][old_j], P2_FIELD_ID);
        assertEquals(table.getTable()[valid_new_i][valid_new_j], P2_KNIGHT_ID);

        tableController.create(table);

        GameController.Turn = Turn_tmp;
    }
}