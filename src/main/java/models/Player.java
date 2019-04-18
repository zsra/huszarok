package models;

import java.util.UUID;

/**
 *  A játékos model célja, hogy név alapján beazonsítja a játékost
 *  és az össz győzelmeit tárolja.
 */
public class Player {

    /**
     * Játékos azonosító.
     */
    private String Id;

    /**
     * A játékos egyedi a neve.
     */
    private String Name;

    /**
     * A játékos összesen elért győzelmeinek a száma.
     */
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

    /**
     * @return Visszaadja a játékos azonosítóját.
     */
    public String getId() {
        return Id;
    }

    /**
     * @return Visszaadja a játékos nevét.
     */
    public String getName() {
        return Name;
    }

    /**
     * @return Visszaadja a játékos összesen megszerzett győzelmeinek számát.
     */
    public int getWins() {
        return Wins;
    }

    /**
     * Ha játékos nyer, akkor nőveli a győzelmeinek számát.
     * @param wins {@code wins} frissített győzelmeknek a száma.
     */
    public void setWins(int wins) {
        Wins = wins;
    }
}