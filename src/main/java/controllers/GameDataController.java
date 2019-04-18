package controllers;

import models.Game;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.List;

/**
 * A játék adatok kezelése. A korábban lejátszott játékok és
 * az aktuális játék kezelése.
 */
public class GameDataController extends IOController {

    /**
     * A játékok {@value #GAMES} fájlba lesznek mentve.
     */
    public static final String GAMES = "games.json";

    /**
     * Az aktuális játék.
     */
    public static Game Session;

    /**
     * Az összes eddig játszott játékok beolvasása.
     *
     * @param typekey <code>typekey</code> Az aktuálisan olvasott elemek osztálya.
     * @param filename <code>filename</code> a json neve ahonnan olvas.
     * @param <T> A beolvasott elemek típusa.
     * @return Vissaadja az összes eddig játszott játékok beolvasása.
     * @throws IOException Ha nem találja a fájlt IO Exception dob.
     */
    @Override
    public <T> List<T> GetAll(Class<?> typekey, String filename) throws IOException {
        return super.GetAll(typekey, filename);
    }

    /**
     *Új játék hozzáadása a korábban játszott játékokhoz.
     *
     * @param element {@code element} a kiírni kívánt elem.
     * @param filename {@code filename} a json neve ahova ír.
     * @param elements {@code elements} Az elemek listája amivel együtt ki lesz írva.
     * @param typekey  {@code typkey} Az aktuálisan kiírt elemek osztálya.
     * @param <T> a kiírott adat típusa.
     * @throws IOException HA nem találja a fájlt IO Exception dob.
     */
    @Override
    public <T> void New(T element, String filename, List<T> elements, Class<?> typekey) throws IOException {
        super.New(element, filename, elements, typekey);
    }

    /**
     * Aaktuális játék során a győzelmek frissítése az adatok között.
     *
     * @param game {@code game} a játék amit update-lünk.
     */
    public void Update(Game game) {
        try {
            List<Game> write = GetAll(Game.class, GAMES);
            for(Game g : write){
                if(g.getId().equals(game.getId())){
                    g = game;
                }
            }
            WriteToJson(write, GAMES, Game.class);

        } catch (IOException e) {
            Logger.error("IO Exception:\t {}", e.getCause());
        }
    }
}
