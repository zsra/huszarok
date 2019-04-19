package models;

/**
 * A belső mátrix konstansai.
 */
public class Table {

    /**
     * Mátrix amin a műveletek végrehajtásra kerülnek.
     */
    private int Table[][];

    /**
     *
     * a belső mátrix visszaadása.
     * @return visszadja a mátrixot.
     */
    public int[][] getTable() {
        return Table;
    }

    /**
     * Egy táblán közvetlenül nem végzünk müveletett, helyete új
     * mátrixot hozunk létre amit itt állítunk be.
     *
     * @param table {@code table} módosítások utáni tábla.
     */
    public void setTable(int[][] table) {
        Table = table;
    }

    /**
     * A mátrix mérete {@value #SIZE}.
     */
    public static final int SIZE = 10;

    /**
     * Neutrális mező azonosítója {@value #NEUTRAL_ID}.
     */
    public static final int NEUTRAL_ID = 0;

    /**
     * Első játékos huszár azonosítója {@value #P1_KNIGHT_ID}.
     */
    public static final int P1_KNIGHT_ID = 1;

    /**
     * Második játékos huszár azonosítója {@value #P2_KNIGHT_ID}.
     */
    public static final int P2_KNIGHT_ID = 2;

    /**
     * Első játékos területazonosítója {@value #P1_FIELD_ID}.
     */
    public static final int P1_FIELD_ID = 3;

    /**
     * Második játékos terüelt azonosítója {@value #P2_FIELD_ID}.
     */
    public static final int P2_FIELD_ID = 4;

    /**
     * Lehetséges léphető mező azonosítója {@value #HIGLIGHT_ID}.
     */
    public static final int HIGLIGHT_ID = 5;

    /**
     * Új tábla objektum létrehozzása és a {@link #Table} deklarálása
     * {@link #SIZE} méretekkel.
      */
    public Table() {
        Table = new int[SIZE][SIZE];
    }
}
