package views;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import models.Player;
import org.pmw.tinylog.Logger;

/**
 * Játék üzenetek kezelése.
 */
public class GameView {

    /**
     *  Ha valamelyik játékos nyer kap egy felugró ablakot, ami
     *  az aktuális játék végét jelenti.
     * @param player {@code player} Győztes játékos
     */
    public void Win(Player player){
        SetDialog(player.getName() + " wins!");
    }

    /**
     *  A játék végét jelző dialogus ablak megjelenítésére szolgáló metódus
     *  győzelem és döntetlen esetén.
     *
     * @param s {@code s} Üzenet
     */
    private void SetDialog(String s) {
        Logger.info("Game end dialog called.");
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Game End");
        dialog.setHeaderText(s);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        dialog.showAndWait();
        dialog.close();
    }

    /**
     *  Ha döntetlen lett a játék vége akkor egy felugró ablakban a döntetlen
     *  üzenet jelenik emg.
     */
    public void Draw(){
        SetDialog("Draw");
    }
}
