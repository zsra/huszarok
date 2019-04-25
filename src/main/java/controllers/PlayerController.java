package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import lombok.Getter;
import lombok.Setter;
import models.Player;
import org.pmw.tinylog.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Játékosok kezelése.
 */
public class PlayerController {

    /**
     * A játékosok akik játszottak {@value #PLAYERS}-be lesznek mentve.
     */
    public static final String PLAYERS = "players.json";

    /**
     * Az első játékos.
     */
    public static Player P1;

    /**
     * A második játékos.
     */
    public static Player P2;

    /**
     * Játékos konténer.
     */
    @Getter
    @Setter
    private List<Player> players;

    /**
     * Új objektum léterehozása után, {@link #PlayerController(InputStream)}
     * új folyam indítása a resource olvasásnál.
     */
    public PlayerController(){
        this(PlayerController.class.getResourceAsStream(PLAYERS));
    }

    /**
     * Kiolvassa a {@link #PLAYERS}-ből a játékosokat.
     *
     * @param is új folyam.
     */
    private PlayerController(InputStream is){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Player>>(){}.getType();
        this.players =gson.fromJson(new InputStreamReader(is),type);
    }

    /**
     * Ellenőrzi, hogy a játékos korábban játszott.
     *
     * @param player a játékos aki ellenőrízve lesz.
     * @return ha játsoztt True-t add vissza, különben False.
     */
    public boolean isNewPlayer(Player player){
        Player result = this.getPlayers().stream()
                .filter(player1 -> player1.getId().equals(player.getId()))
                .findFirst().orElse(null);
        if (result == null){
            return true;
        }

        return false;
    }

    /**
     * Új játékos hozzáadása a többi játékoshoz.
     *
     * @param player új játékos objektum.
     */
    public void New(Player player){
        List<Player> tmp_player = this.getPlayers();
        tmp_player.add(player);
        this.setPlayers(tmp_player);
        Write();
        Logger.info("New element added to {}", PLAYERS);
    }

    /**
     * Ha játékos korábban játszott visszatölti a győzelmeinek számát.
     *
     * @param player a játékos.
     * @return ha játékos játszott visszaadja a korábbi player adatait.
     */
    public Player Load(Player player){
        List<Player> tmp_player = (ArrayList) getPlayers();
        for(Player p : tmp_player){
            if(p.getId().equals(player.getId())){
                return  p;
            }
        }
        Logger.info("Player not found.");
        return null;
    }

    /**
     * Újra kiírja a listát a korábbi helyre.
     *
     */
    private void Write(){
        try {
            Gson gson = new Gson();
            JsonWriter writer = new JsonWriter(
                    new FileWriter("target/classes/controllers/" + PLAYERS));
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

    /**
     * Egy játékos adatainak frissítése.
     *
     * @param player a játékos akinek az adatai frissítve lesznek.
     */
    public void Update(Player player){
        int counter = 0;
        List<Player> tmp_player = (ArrayList) getPlayers();
        for(Player p : tmp_player){
            if(p.getId().equals(player.getId())){
                tmp_player.set(counter, player);
            }
            counter++;
        }
        this.setPlayers(tmp_player);
        Write();
        Logger.info("{} pudated.", player.getName());
    }
}
