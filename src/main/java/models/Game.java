package models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Az osztály definiál két játékos közötti játékot.
 */
public class Game {

    /**
     * Játék azonosító.
     */
    @Getter
    private String id;

    /**
     *  Player1 adatai.
     */
    @Getter
    private Player player1;

    /**
     *  Player2 adatai.
     */
    @Getter
    private Player player2;

    /**
     *  Player1 aktuális játékban lejátszott győzelmeinek száma.
     */
    @Getter
    @Setter
    private int player1_wins;

    /**
     *  Player2 aktuális játékban lejátszott győzelmeinek száma.
     */
    @Getter
    @Setter
    private int player2_wins;

    /**
     * Új játék létrehozása a kostruktor meghívással.
     *
     * @param player1 {@code player1} első játékos objektum.
     * @param player2 {@code player2} második játékos objektum.
     * @param player1_wins {@code player1_wins} első játékos aktuális játékban elért győzelmei.
     * @param player2_wins {@code player2_wins} második játékos aktuális játékban elért győzelmei.
     */
    public Game(Player player1, Player player2, int player1_wins, int player2_wins) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1_wins = player1_wins;
        this.player2_wins = player2_wins;
        this.id = UUID.randomUUID().toString();
    }
}