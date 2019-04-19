package controllers;

import models.Table;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    TableController tableController = new TableController();
    Table table = new Table();

    GameController gameController = new GameController();

    @Test
    void isWin() {
        tableController.create(table);
        int row =  4;
        int[] valid_col = { 2, 3, 4, 5};
        for(int i = 0; i < 4; i++){
            table.getTable()[row][valid_col[i]] = Table.P1_FIELD_ID;
        }
        table.getTable()[row][1] = Table.P1_KNIGHT_ID;

        assertEquals(true, gameController.isWin(table));

        tableController.create(table);

        int col =  4;
        int[] valid_row = { 2, 3, 4, 5};
        int[] non_valid_row = { 2, 3, 7, 5};
        for(int i = 0; i < 4; i++){
            table.getTable()[valid_row[i]][col] = Table.P1_FIELD_ID;
        }
        table.getTable()[6][col] = Table.P1_KNIGHT_ID;

        assertEquals(true, gameController.isWin(table));

        tableController.create(table);

        int[] i = { 4, 5, 6, 7};
        int[] j = { 2, 3, 4, 5};
        int knight_i = 8;
        int knight_j = 6;

        for(int c = 0; c < 4; c++){
            table.getTable()[i[c]][j[c]] = Table.P2_FIELD_ID;
        }
        table.getTable()[knight_i][knight_j] = Table.P2_KNIGHT_ID;

        assertEquals(true, gameController.isWin(table));

    }

    @Test
    void isDraw() {
        tableController.create(table);
        Random random = new Random();
        int counter = 0;
        for(int[] row : table.getTable()){
            if(counter%2  == 0){
                Arrays.fill(row, Table.P1_FIELD_ID);
            } else {
                Arrays.fill(row, Table.P2_FIELD_ID);
            }
            counter++;
        }

        assertEquals(true, gameController.isDraw(table));

        table.getTable()[3][2] = Table.NEUTRAL_ID;
        assertEquals(false, gameController.isDraw(table));
    }

    @Test
    void onRow() {
        tableController.create(table);
        int row =  4;
        int[] valid_col = { 2, 3, 4, 5};
        int[] non_valid_col = { 2, 3, 7, 5};
        for(int i = 0; i < 4; i++){
            table.getTable()[row][valid_col[i]] = Table.P1_FIELD_ID;
        }
        table.getTable()[row][1] = Table.P1_KNIGHT_ID;
        assertEquals(true, gameController.onRow(table));

        tableController.create(table);

        for(int i = 0; i < 4; i++){
            table.getTable()[row][non_valid_col[i]] = Table.P1_FIELD_ID;
        }
        table.getTable()[row][9] = Table.P1_KNIGHT_ID;
        assertEquals(false, gameController.onRow(table));
    }

    @Test
    void onCol() {
        tableController.create(table);
        int col =  4;
        int[] valid_row = { 2, 3, 4, 5};
        int[] non_valid_row = { 2, 3, 7, 5};
        for(int i = 0; i < 4; i++){
            table.getTable()[valid_row[i]][col] = Table.P1_FIELD_ID;
        }
        table.getTable()[6][col] = Table.P1_KNIGHT_ID;
        assertEquals(true, gameController.onCol(table));

        tableController.create(table);

        for(int i = 0; i < 4; i++){
            table.getTable()[non_valid_row[i]][col] = Table.P1_FIELD_ID;
        }
        table.getTable()[6][col] = Table.P1_KNIGHT_ID;
        assertEquals(false, gameController.onCol(table));
    }

    @Test
    void onDiagonal() {
        tableController.create(table);
        int[] i = { 4, 5, 6, 7};
        int[] j = { 2, 3, 4, 5};
        int knight_i = 8;
        int knight_j = 6;

        for(int c = 0; c < 4; c++){
            table.getTable()[i[c]][j[c]] = Table.P2_FIELD_ID;
        }
        table.getTable()[knight_i][knight_j] = Table.P2_KNIGHT_ID;
        assertEquals(true, gameController.onDiagonal(table));
        tableController.create(table);
    }
}