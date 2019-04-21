package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import models.Player;
import org.pmw.tinylog.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * TOP 5 jáétkosnak a kezelése.
 */
public class StandingsController {

    /**
     * Top 5 játékos győzelmek szerint rendezve és beírva.
     */
    public static final String STANDINGS = "top.json";

    /**
     * Visszaadja a top 5 játékost.
     *
     * @return Visszaadja a top 5 játékost.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Új listát állít be, ha megváltozna a tárolt adatok.
     *
     * @param players új lsita.
     */
    private void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * top 5 játékost tároló konténer.
     */
    private List<Player> players;

    /**
     * Új objektum léterehozása után, {@link #StandingsController(InputStream)}
     * új folyam indítása a resource olvasásnál.
     */
    public StandingsController(){
        this(StandingsController.class.getResourceAsStream(STANDINGS));
    }

    /**
     * Kiolvassa a resource-ből az adatokat, új objektum létrehozzása után.
     *
     * @param is új folyam.
     */
    private StandingsController(InputStream is){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Player>>(){}.getType();
        this.players =gson.fromJson(new InputStreamReader(is),type);
    }

    /**
     * Kiszedi a top 5 játékost a players.json-ből
     */
    public void Update(){
        PlayerController controller = new PlayerController();
        List<Player> top = controller.getPlayers()
                .stream()
                .sorted(Comparator.comparingInt(Player::getWins).reversed())
                .limit(5)
                .collect(Collectors.toList());
        this.setPlayers(top);
        Write();
    }

    /**
     * Újra kiírja a listát a korábbi helyre.
     *
     */
    private void Write(){
        try {
            Gson gson = new Gson();
            JsonWriter writer = new JsonWriter(
                    new FileWriter("target/classes/controllers/" + STANDINGS));
            writer.setIndent("  ");
            writer.beginArray();
            this.getPlayers().stream()
                    .forEach(element -> gson.toJson(element, Player.class ,writer));
            writer.endArray();
            writer.close();
            Logger.info("Data has rewritten.");
        } catch (IOException e){
            Logger.error("IO Exception:\t {}", e.getCause());
        }

    }

}
