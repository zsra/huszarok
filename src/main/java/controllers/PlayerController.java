package controllers;

import models.Player;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Játékosok kezelése.
 */
public class PlayerController extends IOController {

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
     * A játékosok kiovasása {@value #PLAYERS}-ból.
     * {@inheritDoc}
     *
     * @param typekey {@code typekey} Az aktuálisan olvasott elemek osztálya.
     * @param filename {@code filename} a json neve ahonnan olvas.
     * @param <T> a Player típus.
     * @return Visszaadja az összes játékost.
     * @throws IOException Ha nem találja a fájlt IO Exception dob.
     */
    @Override
    public <T> List<T> GetAll(Class<?> typekey, String filename) throws IOException {
        return super.GetAll(typekey, filename);
    }

    /**
     * Hozzáadd a korábbi játékosokhoz egy újat.
     * {@inheritDoc}
     *
     * @param element {@code element} a kiírni kívánt elem.
     * @param filename {@code filename} a json neve ahova ír.
     * @param elements {@code elements} Az elemek listája amivel együtt ki lesz írva.
     * @param typekey {@code typekey} Az aktuálisan kiírt elemek osztálya.
     * @param <T> a Player típus.
     * @throws IOException Ha nem találja a fájlt IO Exception dob.
     */
    @Override
    public <T> void New(T element, String filename, List<T> elements, Class<?> typekey) throws IOException {
        super.New(element, filename, elements, typekey);
    }

    /**
     * Egy játékos adatinak frissítése.
     *
     * @param player {@code player} a játékos akinek az adatai frissítve lesznek.
     */
    public void Update(Player player) {
        try {
            int counter = 0;
            List<Player> write = (ArrayList) GetAll(Player.class, PLAYERS);
            for(Player p : write){
                if(p.getId().equals(player.getId())){
                    write.set(counter, player);
                    WriteToJson(write, PLAYERS, Player.class);
                    Logger.info("{} updated.", player.getName());
                }
                counter++;
            }
            Logger.warn("Player not found!");
        } catch (IOException e) {
            Logger.error("IO Exception:\t {}", e.getCause());
        }
    }

    /**
     * Mini feature. Ha a játékos korábban játszott (név alapján),
     * akkor visszaadja a győzelmeit.
     *
     * @param in_player {@code in_player} az új játékos adati.
     * @return Ha név már szerepelt True, különben False.
     */
    public boolean isNewPalyer(Player in_player){
        PlayerController controller = new PlayerController();
        try {
            List<Player> players = controller.GetAll(Player.class, PLAYERS);
            Player tmp = players.stream()
                    .filter(p -> p.getName().equals(in_player.getName()))
                    .limit(1).findFirst().orElse(null);
            if(tmp == null){
                Logger.info("{} already played this game.", in_player.getName());
                return false;
            }
        } catch (IOException e) {
            Logger.error("IO Exception:\t {}", e.getCause());
        }
        return true;
    }

    /**
     * Játékos betöltése.
     *
     * @param player {@code player} a játékos akinek az adati vissza lesznek töltve.
     * @return Visszaaddja a játékost, ha szerepel.
     */
    public Player Load(Player player){
        PlayerController controller = new PlayerController();
        try {
            List<Player> players = controller.GetAll(Player.class, PLAYERS);
            Player tmp = players.stream()
                    .filter(player1 -> player1.getId().equals(player.getId()))
                    .limit(1).findFirst().orElse(null);
            if(tmp != null){
                Logger.info("{} wins reloaded.", tmp.getName());
                return tmp;
            }
        } catch (IOException e) {
            Logger.error("IO Exception:\t {}", e.getCause());
        }
        Logger.warn("Player not found!");
        return player;
    }
}
