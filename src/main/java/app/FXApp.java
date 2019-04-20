package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pmw.tinylog.Logger;
import views.AnchorView;


/**
 * @author Zsupos Richárd András.
 * 2.28 többjátékos feladat.
 */
public class FXApp extends Application {

    /**
     * pane hozzáadása a stagehez és grafikus felület
     * hozzárendelés.
     *
     * @param stage <code>stage</code> konténer.
     * @throws Exception Ha nem tudd OpenJFX tulajdonsághoz hozzáférni
     * Exception dobb.
     */
    @Override
    public void start(Stage stage) throws Exception {

        Logger.info("AnchorPane added to Stage.");
        AnchorView root = new AnchorView();
        stage.setScene(new Scene(root, 840, 620));
        stage.setTitle("huszarok");
        Logger.info("GUI loaded...");
        stage.show();
    }

    /**
     * Lancher main.
     *
     * @param args <code>args</code> CL argument.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
