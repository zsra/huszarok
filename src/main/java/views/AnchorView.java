package views;

import controllers.TableController;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import models.Table;
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
                "#c9c9c9");
        TableController tableController = new TableController();

        Table table = new Table();

        tableController.create(table);

        TableView.create();

        FlowPane pane = new FlowPane();
        pane.setStyle("-fx-min-height: 610; -fx-min-width: 610; -fx-padding: 10 0 0 10");
        this.getChildren().addAll(pane);

        for(int i = 0; i < SIZE; i++){
            for(int j =0; j < SIZE; j++){
                TableView.fields[i][j].setOnMouseClicked(t -> move(t, table));
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
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    private void move(javafx.scene.input.MouseEvent t, Table table) {
        TableController controller = new TableController();

        int[][] tmp = table.getTable();

        Logger.info("Clicked.");
        Label lab = (Label) t.getSource();
        int[] pos = getPosition(lab.getId());

        if(Turn == 0){
            controller.place(pos[0], pos[1] ,table);
            Turn++;
        } else if(Turn == 1) {
            controller.place(pos[0], pos[1], table);
            Turn++;
        }
        else {
            if(!isMoved){
                if(controller.isKnight(pos[0], pos[1],table)){
                    if(tmp[pos[0]][pos[1]] == P1_KNIGHT_ID
                    && Turn%2 == 0){
                        isMoved = true;
                        controller.highlight(pos[0], pos[1], table);
                        oldknightPos[0] = pos[0];
                        oldknightPos[1] = pos[1];
                        TableView.refresh(table);
                    }else if(tmp[pos[0]][pos[1]] == P2_KNIGHT_ID
                            && Turn%2 != 0) {
                        isMoved = true;
                        controller.highlight(pos[0], pos[1], table);
                        oldknightPos[0] = pos[0];
                        oldknightPos[1] = pos[1];
                        TableView.refresh(table);
                    }
                }
            } else {
                if(controller.isHighlighted(pos[0], pos[1], table)){
                    controller.move(oldknightPos[0], oldknightPos[1],
                    pos[0], pos[1], table);
                    isMoved = false;
                }

            }
        }
        TableView.refresh(table);
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
