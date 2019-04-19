package controllers;

import models.Table;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TableControllerTest {

    private static final int SIZE  = 10;
    private static int[][] TestTable = new int[SIZE][SIZE];

    private static final int P1_KNIGHT_ID = Table.P1_KNIGHT_ID;
    private static final int P2_KNIGHT_ID = Table.P2_KNIGHT_ID;

    @Test
    void createController() {
        int[] rows = new int[10];
        Arrays.fill(rows, 0);
        for(int[] row : TestTable){
            Arrays.fill(row,0);
        }
        for(int[] row : TestTable){
            assertArrayEquals(row, rows);
        }
    }

    @Test
    void place() {

    }

    @Test
    void highlight() {
    }

    @Test
    void isHighlighted() {
    }

    @Test
    void isKnight() {
    }

    @Test
    void move() {
    }
}