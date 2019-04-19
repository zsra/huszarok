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
                tableController.highlight(i,j, table);
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
    }

    @Test
    void rehighlighted() {
    }

    @Test
    void isKnight() {
    }

    @Test
    void move() {
    }
}