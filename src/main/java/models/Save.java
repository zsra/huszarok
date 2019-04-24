package models;

import java.util.UUID;

public class Save {

    private String id;

    private int[][] table;

    private int turn;

    private Game game;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Save(int[][] table, int turn, Game game) {
        this.id = UUID.randomUUID().toString();
        this.table = table;
        this.turn = turn;
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }
}
