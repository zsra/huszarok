package controllers;

import models.Player;

import java.io.IOException;
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
     *
     * @param typekey Az aktuálisan olvasott elemek osztálya.
     * @param filename a json neve ahonnan olvas.
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
     *
     * @param element a kiírni kívánt elem.
     * @param filename a json neve ahova ír.
     * @param elements Az elemek listája amivel együtt ki lesz írva.
     * @param typekey Az aktuálisan kiírt elemek osztálya.
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
     * @param player a játékos akinek az adatai frissítve lesznek.
     */
    public void Update(Player player) {
        try {
            List<Player> write = GetAll(Player.class, PLAYERS);
            for(Player p : write){
                if(p.getId().equals(player.getId())){
                    p = player;
                }
            }
            WriteToJson(write, PLAYERS, Player.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mini feature. Ha a játékos korábban játszott (név alapján),
     * akkor visszaadja a győzelmeit.
     *
     * @param in_player az új játékos adati.
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
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Játékos betöltése.
     *
     * @param player a játékos akinek az adati vissza lesznek töltve.
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
                return tmp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }
}
