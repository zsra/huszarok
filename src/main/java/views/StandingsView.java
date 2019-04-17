package views;

import controllers.StandingsController;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import models.Player;

import java.io.IOException;
import java.util.List;

/**
 * A standings nézetett kezeli.
 */
public class StandingsView extends Button {

    /**
     * Ha játékosok rákattintanak a standings gombra,
     * felugra ablakban megjeleníti az 5 legtöbb
     * gyözelemmel rendelkező játékosokat csökkenő sorrendben.
     */
    public StandingsView(){
        this.setText("Standings");
        this.minHeight(40);
        this.minWidth(100);
        this.setLayoutX(690);
        this.setLayoutY(500);
        this.setOnMouseClicked(t -> {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Standings");
            dialog.setHeaderText("Player\tWins");
            dialog.setContentText("");
            StandingsController standingsController
                    = new StandingsController();
            StandingsController standingsController1 = new StandingsController();
            standingsController.Refresh();
            try {
                List<Player> players = standingsController.GetAll(Player.class,
                        StandingsController.STANDINGS);
                players.stream().limit(5).forEach(p ->
                        dialog.setContentText(
                                dialog.getContentText()
                                        + "\n" + p.getName() + "\t" + p.getWins()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            dialog.close();
        });
    }
}
