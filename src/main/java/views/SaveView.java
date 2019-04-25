package views;

import controllers.DataController;
import controllers.GameController;
import controllers.SaveController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import models.Save;
import models.Table;
import org.pmw.tinylog.Logger;

/**
 * A metés elkészítése és mentésének grafikus vezérlői.
 */
public class SaveView extends Button {

    /**
     * Új obj. létrehozzásánál egy új gombnak add értéket, ami
     * elmenti az aktuális játék állást.
     *
     * @param table {@code table} a tábla ami elmentésre kerül.
     */
    public SaveView(Table table){
        this.setText("Save");
        this.setPrefSize(150,50);
        this.setLayoutX(650);
        this.setLayoutY(450);
        this.setOnMouseClicked(t -> {this.save(table);});
    }

    /**
     * A tábla elmentése és üznettel kiírja az azonosítót, ami alapján
     * a visszatöltés megtörténik.
     *
     * @param table {@code } A tábla obj. átadása a tábla elmentésére.
     */
    public void save(Table table){
        Save tmp = new Save(table.getTable(), GameController.Turn, DataController.Session);
        SaveController saveController = new SaveController();
        saveController.Add(tmp);
        Logger.info("Game saved!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game saved!");
        alert.setContentText("Save Id: " + tmp.getId());
        alert.showAndWait();
    }
}
