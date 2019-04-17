package models;

import java.util.UUID;

/**
 * Az osztály definiál két játékos közötti játékot.
 */
public class Game {

    /**
     * Játék azonosító.
     */
    private String id;

    /**
     *  Player1 adatai.
     */
    private Player player1;

    /**
     *  Player2 adatai.
     */
    private Player player2;

    /**
     *  Player1 aktuális játékban lejátszott győzelmeinek száma.
     */
    private int player1_wins;

    /**
     *  Player2 aktuális játékban lejátszott győzelmeinek száma.
     */
    private int player2_wins;

    /**
     * Új játék létrehozása a kostruktor meghívással.
     *
     * @param player1 első játékos objektum.
     * @param player2 második játékos objektum.
     * @param player1_wins első játékos aktuális játékban elért győzelmei.
     * @param player2_wins második játékos aktuális játékban elért győzelmei.
     */
    public Game(Player player1, Player player2, int player1_wins, int player2_wins) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1_wins = player1_wins;
        this.player2_wins = player2_wins;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * @return Visszaadja az első játékos Player objektumot.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * @return Visszaadja a második játékos Player objektumot.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * @return Visszaadja az első játékos aktuális győzelmeit.
     */
    public int getPlayer1_wins() {
        return player1_wins;
    }

    /**
     * Első játékos győzelmeinek számának növelése.
     *
     * @param player1_wins új győzelem hozzáadása.
     */
    public void setPlayer1_wins(int player1_wins) {
        this.player1_wins = player1_wins;
    }

    /**
     * @return Visszaadja a második játékos aktuális győzelmeit.
     */
    public int getPlayer2_wins() {
        return player2_wins;
    }

    /**
     * Második játékos győzelmeinek számának növelése.
     *
     * @param player2_wins új győzelem hozzáadása.
     */
    public void setPlayer2_wins(int player2_wins) {
        this.player2_wins = player2_wins;
    }

    /**
     * @return Visszaadja a játék azonosítóját.
     */
    public String getId() {
        return id;
    }
}