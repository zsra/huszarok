package models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Mentés objektum a mentés készítéséhez.
 */
public class Save {

    /**
     * Mentés azonosítója ami alapján visszatöltés
     * történik.
     */
    @Getter
    private String id;

    /**
     * A tábla aktluális állása ami elmentésre kerül.
     */
    @Getter
    @Setter
    private int[][] table;

    /**
     * Mentés elött lejátszott körök száma.
     */
    @Getter
    private int turn;

    /**
     * Játék információk, állás, jáékosok.
     */
    @Getter
    private Game game;

    /**
     * Mentés konstruktor. A mentés során elmentésre kerülnek
     * azok az elemek ami ahhoz szükséges, hogy vissza lehessen
     * tölteni.
     *
     * @param table {@code table} a tábla ami mentésre kerül.
     * @param turn {@code turn} a aktuális kör száma.
     * @param game {@code game} a aktuális játék.
     */
    public Save(int[][] table, int turn, Game game) {
        this.id = UUID.randomUUID().toString();
        this.table = table;
        this.turn = turn;
        this.game = game;
    }
}
