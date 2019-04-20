package views;

import controllers.GameDataController;
import controllers.PlayerController;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import models.Game;
import models.Player;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static controllers.GameDataController.GAMES;
import static controllers.PlayerController.P1;
import static controllers.PlayerController.P2;
import static controllers.PlayerController.PLAYERS;

/**
 * Játékos adatainak bekérése és megjelenítése a GUI-n.
 */
public class PlayerView {

    /**
     * Első játékos nevét tartalmaző Label.
     */
    public static Label P1_label = new Label("");

    /**
     * Második játékos nevét tartalmazó Label.
     */
    public static Label P2_label = new Label("");

    /**
     * Eredményjelző.
     */
    public static Label Stat = new Label("");

    /**
     * Player változók inicializálása.
     */
    public static void Init(){
        PlayerView view = new PlayerView();
        view.SetLabel();
        view.SetStat();
    }

    /**
     * Beállíítja a GUI-n a játékos Labeleket.
     */
    private void SetLabel(){
        PlayerView view = new PlayerView();
        view.setPlayers();

        P1_label.setText("P1: " + P1.getName());
        P2_label.setText("P2: " + P2.getName());

        P1_label.setLayoutX(650);
        P2_label.setLayoutX(650);

        P1_label.setLayoutY(200);
        P2_label.setLayoutY(250);

        P1_label.setFont(new Font(30));
        P2_label.setFont(new Font(30));
    }

    /**
     * Lehív két dialog ablakot ahol bekéri elsőnek az
     * első játékos nevét és a második játékos nevét.
     */
    public void setPlayers(){
        NameDialog("P1");
        NameDialog("P2");
    }

    /**
     * Megjeleníti a dialog ablakokat, ahol bekérésre kerülnek a két játékos
     * neve, elindít egy új session-t és, ha játékos korábban játszott
     * betölti a név alapján a győzelminek számát. {@link #setPlayers()} hívja meg.
     *
     * @param str_plr {@code str_plr} melyik játékos adatait kéri be.
     */
    private void NameDialog(String str_plr) {
        TextInputDialog dialog = new TextInputDialog("Player");
        PlayerController playerController = new PlayerController();
        dialog.setTitle("Player name");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if(str_plr.equals("P1")){
                P1 = new Player(name, 0);
                if(playerController.isNewPalyer(P1)){
                    Logger.info("New Player.");
                    try {
                        playerController.New(
                                P1,
                                PLAYERS,
                                playerController.GetAll(
                                        Player.class, PLAYERS)
                                        .stream().collect(Collectors.toList()),Player.class);
                    } catch (IOException e) {
                        Logger.error("IO Exception:\t {}", e.getCause());
                    }

                } else {
                    P1 = playerController.Load(P1);
                }
            } else {
                P2 = new Player(name, 0);
                if(playerController.isNewPalyer(P2)){
                    Logger.info("New Player.");
                    try {
                        playerController.New(
                                P2,
                                PLAYERS,
                                playerController.GetAll(
                                        Player.class, PLAYERS)
                                        .stream().collect(Collectors.toList()),Player.class);
                    } catch (IOException e) {
                        Logger.error("IO Exception:\t {}", e.getCause());
                    }

                } else {

                    P2 = playerController.Load(P2);
                }
                GameDataController.Session = new Game(P1, P2, 0, 0);
                GameDataController gameDataController = new GameDataController();
                try {
                    gameDataController.New(GameDataController.Session, GAMES,
                            gameDataController.GetAll(Game.class, GAMES)
                                    .stream().collect(Collectors.toList()), Game.class);
                } catch (IOException e) {
                    Logger.error("IO Exception:\t {}", e.getCause());
                }
            }
        });
    }


    /**
     * Beállítja a stat label-t ahol jelzi a játék állását.
     */
    private void SetStat(){
        Stat.setText(P1.getWins() + " - "+ P2.getWins());
        Stat.setLayoutY(300);
        Stat.setLayoutX(690);
        Stat.setFont(new Font(30));
    }

    /**
     * Frissíti az állást.
     */
    public void Update(){
        Stat.setText(GameDataController.Session.getPlayer1_wins()
                + " - "+ GameDataController.Session.getPlayer2_wins());
    }
}
