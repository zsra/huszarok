package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import models.Game;
import org.pmw.tinylog.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A játékok file-ban való tárolása.
 */
public class DataController {

    /**
     * A játékok {@value #GAMES}-be lesznek mentve.
     */
    public static final String GAMES = "games.json";

    /**
     * Aktuális játék adatainak tárolása.
     */
    public static Game Session = new Game(null,null,0,0);

    /**
     * {@link #DataController(InputStream)} meghívása, resource lekérése.
     */
    public DataController(){
        this(DataController.class.getResourceAsStream(GAMES));
    }

    /**
     * Visszaadja az összes eddig lejátoszott játékot.
     *
     * @return Visszaadja az összes eddig lejátoszott játékot.
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Ha módusítjuk egy játékot újraírjuk a resource-t.
     *
     * @param games a játékok listája.
     */
    private void setGames(List<Game> games) {
        this.games = games;
    }

    /**
     * Játékokat tároló konténer.
     */
    private List<Game> games;

    /**
     * lehívja az erőforrásokat új példány létrehozzásakor.
     *
     * @param is új folyam indítása.
     */
    private DataController(InputStream is){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Game>>(){}.getType();
        this.games =gson.fromJson(new InputStreamReader(is),type);
    }

    /**
     * Újraírja a resource-t. a módosított listával.
     */
    private void Write(){
        try {
            Gson gson = new Gson();
            JsonWriter writer = new JsonWriter(
                    new FileWriter("target/classes/controllers/" + GAMES));
            writer.setIndent("  ");
            writer.beginArray();
            this.getGames().stream()
                    .forEach(element -> gson.toJson(element, Game.class ,writer));
            writer.endArray();
            writer.close();
            Logger.info("Data has rewritten.");
        } catch (IOException e){
            Logger.error("IO Exception:\t {}", e.getCause());
        }
    }

    /**
     *
     * Új elem ohzzáadása a listához majd újra kiírása {@link #Write()}
     * metódussal.
     *
     * @param game új játék ami ohzzá lesz adva a korábban játszotakkal.
     */
    public void New(Game game){
        List<Game> tmp_games = this.getGames();
        tmp_games.add(game);
        this.setGames(tmp_games);
        Write();
    }

    /**
     * Ha az aktuális játék állása megváltozik újra lesz írva a z értékei a
     * {@link #GAMES}.
     *
     * @param game
     */
    public void Update(Game game){
        List<Game> tmp_games = (ArrayList) this.getGames();
        int counter  = 0;
        for(Game g : tmp_games){
            if(g.getId().equals(game.getId())){
                tmp_games.set(counter, game);
            }
            counter++;
        }
        this.setGames(tmp_games);
        Write();

    }
}
