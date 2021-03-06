package views;

import controllers.StandingsController;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import models.Player;
import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.util.List;

/**
 * A standings nézetett kezeli.
 */
public class StandingsView extends Button {

    /**
     * A top 5 játékost összegyűjtő string.
     */
    public static String top = "";

    /**
     * Ha játékosok rákattintanak a standings gombra,
     * felugra ablakban megjeleníti az 5 legtöbb
     * gyözelemmel rendelkező játékosokat csökkenő sorrendben.
     */
    public StandingsView(){
        Logger.info("Standings dialog called.");
        this.setText("Standings");
        this.setPrefSize(150,50);
        this.setLayoutX(650);
        this.setLayoutY(550);
        this.setOnMouseClicked(t -> {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Standings");
            dialog.setHeaderText("Player\tWins");
            dialog.getDialogPane().setPrefSize(300,400);
            StandingsController standingsController
                    = new StandingsController();
            StandingsController standingsController1 = new StandingsController();
            standingsController.Update();
            top = "";
            List<Player> players = standingsController.getPlayers();
            players.stream().limit(5).forEach(p -> {
                top += (p.getName() + "\t" + "  " + p.getWins() + "\n");

            });
            dialog.getDialogPane().setContent(
                    new Label("" + top));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            dialog.close();
        });
    }
}
