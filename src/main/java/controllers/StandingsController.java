package controllers;

import models.Player;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * TOP 5 jáétkosnak a kezelése.
 */
public class StandingsController extends IOController {

    /**
     * Top 5 játékos győzelmek szerint rendezve és beírva.
     */
    public static final String STANDINGS = "top.json";

    /**
     * Visszadja a TOP 5 játékos győzelmek szerint.
     * {@inheritDoc}
     *
     * @param typekey {@code typekey} Az aktuálisan olvasott elemek osztálya.
     * @param filename {@code filename} a json neve ahonnan olvas.
     * @param <T> Player típus.
     * @return Visszaadja az 5 legjobb játékost.
     * @throws IOException ha nem találja a fájlt IO Exception dob.
     */
    @Override
    public <T> List<T> GetAll(Class<?> typekey, String filename) throws IOException {
        return super.GetAll(typekey, filename);
    }

    /**
     * Kiírja az 5 legjobb játékost.
     * {@inheritDoc}
     *
     * @param tmp {@code tmp} A lista aminek az elemeivel felül lesz írva az állomány.
     * @param filename {@code filename} a json neve ahova ír.
     * @param typekey {@code typekey} Az aktuálisan kiírt elemeknek osztálya.
     * @param <T> Player típus.
     * @throws IOException Ha nem találja a fájlt IO Exception dob.
     */
    @Override
    public <T> void WriteToJson(List<T> tmp, String filename, Class<?> typekey) throws IOException {
        super.WriteToJson(tmp, filename, typekey);
    }

    /**
     * Frissíti az 5 legjobb játékosoknak a listáját.
     */
    public void Refresh(){
        try {
            PlayerController playerController = new PlayerController();
            List<Player> players = playerController.GetAll(Player.class,
                    PlayerController.PLAYERS);
            List<Player> standings = players
                    .stream()
                    .sorted(Comparator.comparingInt(Player::getWins).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            WriteToJson(standings, STANDINGS, Player.class);
            Logger.info("Standings updated.");
        } catch (IOException e) {
            Logger.error("IO Exception:\t {}", e.getCause());
        }
    }
}
