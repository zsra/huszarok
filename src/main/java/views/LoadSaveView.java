package views;

import controllers.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import models.Table;
import org.pmw.tinylog.Logger;

import java.util.Optional;

public class LoadSaveView extends Button {

    public LoadSaveView(Table table, TableController tableController){
        this.setPrefSize(150,50);
        this.setLayoutX(650);
        this.setLayoutY(550);
        this.setOnMouseClicked(t -> {Load(table, tableController);});
    }

    public void Load(Table table, TableController tableController){
        TextInputDialog dialog = new TextInputDialog("Load saves");
        dialog.setTitle("Load Game");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            SaveController saveController = new SaveController();
            if(saveController.getSaveById(name) == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("Invalid save name.");
                Logger.warn("Invalid save name.");
            } else {
                saveController.Load(name, table, tableController);
                UpdateGUI();
            }
        });
    }

    public void UpdateGUI(){
        PlayerView.P1_label.setText("P1: " + DataController.Session.getPlayer1().getName());
        PlayerView.P2_label.setText("P2: " + DataController.Session.getPlayer2().getName());
        PlayerView playerView = new PlayerView();
        playerView.Update();
    }

}
