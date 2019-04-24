package views;

import controllers.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import models.Table;
import org.pmw.tinylog.Logger;

import java.util.Optional;

/**
 * A mentés betöltésére szolgálló gomb létrehozzása és
 * a felugró ablakok kezelése.
 */
public class LoadSaveView extends Button {

    /**
     * Új objektum létrehozzásánál egy gombot definiál aminek
     * megnyomásával felugrik egy ablak, ahol stringet vár és
     * az alapján fogja visszatölteni a mentést.
     *
     * @param table {@code table} a tábla a modósításhoz.
     * @param tableController {@code tableController} a controller meghívása.
     */
    public LoadSaveView(Table table, TableController tableController){
        this.setPrefSize(150,50);
        this.setLayoutX(650);
        this.setLayoutY(550);
        this.setOnMouseClicked(t -> {Load(table, tableController);});
    }

    /**
     * A betöltés elindítása. a betöltésnél egy stringet vár ami alapján
     * betöltést kezdeményez.
     *
     * @param table {@code table} a tábla a modósításhoz.
     * @param tableController {@code tableController} a controller meghívása.
     */
    public void Load(Table table, TableController tableController){
        TextInputDialog dialog = new TextInputDialog("Load saves");
        dialog.setTitle("Load Game");
        dialog.setContentText("Save Id:");
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

    /**
     * Frissíti a táblán kívüli elemeket a GUI-n. (játékosnév, állás).
     */
    public void UpdateGUI(){
        PlayerView.P1_label.setText("P1: " + DataController.Session.getPlayer1().getName());
        PlayerView.P2_label.setText("P2: " + DataController.Session.getPlayer2().getName());
        PlayerView playerView = new PlayerView();
        playerView.Update();
    }

}
