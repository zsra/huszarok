package models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 *  A játékos model célja, hogy név alapján beazonsítja a játékost
 *  és az össz győzelmeit tárolja.
 */
public class Player {

    /**
     * Játékos azonosító.
     */
    @Getter
    private String Id;

    /**
     * A játékos egyedi a neve.
     */
    @Getter
    private String Name;

    /**
     * A játékos összesen elért győzelmeinek a száma.
     */
    @Getter
    @Setter
    private int Wins;

    /**
     * Új játékos objektum létrehozzására vagy betöltés
     * utánra való tárolása.
     * @param name {@code name} A játékos egyedi neve.
     * @param wins {@code wins} A játékos győzelmeinek száma, ha új a játétkos 0-at kap.
     */
    public Player(String name, int wins) {
        Name = name;
        Wins = wins;
        Id = UUID.randomUUID().toString();
    }
}