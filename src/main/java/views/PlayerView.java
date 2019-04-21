package views;

import controllers.DataController;
import controllers.PlayerController;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import models.Game;
import models.Player;
import org.pmw.tinylog.Logger;

import java.util.Optional;

import static controllers.PlayerController.P1;
import static controllers.PlayerController.P2;

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
                if(playerController.isNewPlayer(P1)){
                    Logger.info("New Player.");
                    playerController.New(P1);

                } else {
                    P1 = playerController.Load(P1);
                }
            } else {
                P2 = new Player(name, 0);
                if(playerController.isNewPlayer(P2)){
                    Logger.info("New Player.");
                    playerController.New(P2);

                } else {
                    P2 = playerController.Load(P2);
                }
                DataController.Session = new Game(P1, P2, 0, 0);
                DataController gameDataController = new DataController();
                gameDataController.New(DataController.Session);
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
        Stat.setText(DataController.Session.getPlayer1_wins()
                + " - "+ DataController.Session.getPlayer2_wins());
    }
}
