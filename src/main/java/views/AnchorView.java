package views;

import controllers.TableController;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.pmw.tinylog.Logger;

import java.util.logging.LoggingPermission;

import static models.Table.*;
import static controllers.GameController.*;

/**
 * A panel amin az összes vezérlő elhelyezkedik.
 */
public class AnchorView extends AnchorPane {

    /**
     * Azt ellenőrzi, hogy a kíválasztott huszár lépet-e.
     */
    private static boolean isMoved = false;

    /**
     * Elmenti a huszár pozicióját ahonnan indul.
     */
    private static int[] oldknightPos = new int[2];

    /**
     * Kostruktorral huzzárendeli az elemek és értékeket
     * add nekik.
     */
    public AnchorView(){

        this.minHeight(840);
        this.minWidth(620);
        this.setStyle("-fx-background-color: \n" +
                "#ececec");
        TableController.createController();
        TableView.createView();

        FlowPane pane = new FlowPane();
        pane.setStyle("-fx-min-height: 610; -fx-min-width: 610; -fx-padding: 10 0 0 10");
        this.getChildren().addAll(pane);

        for(int i = 0; i < SIZE; i++){
            for(int j =0; j < SIZE; j++){
                TableView.fields[i][j].setOnMouseClicked(t -> move(t));
                pane.getChildren().addAll(TableView.fields[i][j]);
            }
        }
        StandingsView standingsView = new StandingsView();
        this.getChildren().addAll(standingsView);

        PlayerView.Init();
        this.getChildren().addAll(PlayerView.P1_label,
                PlayerView.P2_label, PlayerView.Stat);

    }

    /**
     * Klikk esemény során lehívott metódus, ami elindítja
     * a háttérfolyamatokat.
     *
     * @param t {@code t} Click Event.
     */
    private void move(javafx.scene.input.MouseEvent t) {
        Logger.info("Clicked.");
        Label lab = (Label) t.getSource();
        int[] pos = getPosition(lab.getId());
        if(Turn == 0){
            TableController.place(pos[0], pos[1]);
            Turn++;
        } else if(Turn == 1) {
            TableController.place(pos[0], pos[1]);
            Turn++;
        }
        else {
            if(!isMoved){
                if(TableController.isKnight(pos[0], pos[1])){
                    if(TableController.Table[pos[0]][pos[1]] == P1_KNIGHT_ID
                    && Turn%2 == 0){
                        isMoved = true;
                        TableController.highlight(pos[0], pos[1]);
                        oldknightPos[0] = pos[0];
                        oldknightPos[1] = pos[1];
                        TableView.refresh();
                    }else if(TableController.Table[pos[0]][pos[1]] == P2_KNIGHT_ID
                            && Turn%2 != 0) {
                        isMoved = true;
                        TableController.highlight(pos[0], pos[1]);
                        oldknightPos[0] = pos[0];
                        oldknightPos[1] = pos[1];
                        TableView.refresh();
                    }
                }
            } else {
                if(TableController.isHighlighted(pos[0], pos[1])){
                    TableController.move(oldknightPos[0], oldknightPos[1],
                    pos[0], pos[1]);
                    isMoved = false;
                }

            }
        }

        TableView.refresh();
    }

    /**
     * Egy label Id-ből kiszámol egy poziciót. Az id megegyik
     * a mátrixban elfoglalt helyével.
     *
     * @param id {@code id} Label azonosító
     * @return visszadd egy tömböt, 0 indexen a sor indexet,
     * 1-es indexen az oszlop indexet.
     */
    public int[] getPosition(String id){
        int[] result = new int[2];
        char[] chrs = id.toCharArray();
        if(chrs.length == 1){
            result[0] = 0;
            result[1] = Character.getNumericValue(chrs[0]);
        }
        else {
            result[0] = Character.getNumericValue(chrs[0]);
            result[1] = Character.getNumericValue(chrs[1]);
        }
        return result;
    }
}
