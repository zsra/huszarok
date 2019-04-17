package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
     * @param stage konténer.
     * @throws Exception Ha nem tudd OpenJFX tulajdonsághoz hozzáférni
     * Exception dobb.
     */
    @Override
    public void start(Stage stage) throws Exception {

        AnchorView root = new AnchorView();
        stage.setScene(new Scene(root, 840, 620));
        stage.show();
    }

    /**
     * Lancher main.
     *
     * @param args CL argument.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
