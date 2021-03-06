package views;

import controllers.GameController;
import controllers.TableController;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.Table;
import org.pmw.tinylog.Logger;

import static models.Table.*;
import static controllers.TableController.*;

/**
 * A tábla megjelenítése.
 */
public class TableView {

    /**
     * Labelek egy 10x10 mátrixa, egy label egy mezőt reprezentál.
     */
    public static Label[][] fields = new Label[SIZE][SIZE];

    /**
     * Inicializálja a labeleket. ID megadása, ami reprezentálja a
     * {@link #fields} elfoglalt helyét. Ezenekívűl bordert állít.
     */
    public static void create(){
        Logger.info("View created.");
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                fields[i][j] = new Label("");
                fields[i][j].setId(((i * 10) + j) + "");
                fields[i][j].setBorder(new Border(new BorderStroke
                        (Color.BLACK, BorderStrokeStyle.SOLID,
                                null, new BorderWidths(1))));
                fields[i][j].setFont(Font.font("sans-serif", FontWeight.BOLD, 38));
                grid(i, j);
            }
        }
    }

    /**
     * Újraszámolja a színeket és a huszár poizicójáta belső mátrix
     * alapján.
     * @param table {@code table} table objektum, ami végig van
     *                           ráncigálva az egész kódon...
     */
    public static void refresh(Table table){
        int [][] tmp = table.getTable();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                switch (tmp[i][j]){
                    case P1_KNIGHT_ID : {
                        fields[i][j].setStyle("-fx-background-color: #f4fc58;" +
                                "-fx-min-width: 60;-fx-min-height: 60; -fx-font-size: 38;");
                        fields[i][j].setText(" \u2658");
                    } break;
                    case P2_KNIGHT_ID : {
                        fields[i][j].setStyle("-fx-background-color: #44efff;" +
                                "-fx-min-width: 60;-fx-min-height: 60; -fx-font-size: 38;");
                        fields[i][j].setText(" \u265E");
                    } break;
                    case P1_FIELD_ID: {
                        fields[i][j].setStyle("-fx-background-color: #f4fc58;" +
                                "-fx-min-width: 60;-fx-min-height: 60;");
                        fields[i][j].setText("");
                    } break;
                    case P2_FIELD_ID: {
                        fields[i][j].setStyle("-fx-background-color: #44efff;" +
                                "-fx-min-width: 60;-fx-min-height: 60;");
                        fields[i][j].setText("");
                    } break;
                    case HIGLIGHT_ID: {
                        fields[i][j].setStyle("-fx-background-color: #000000;" +
                                "-fx-min-width: 60;-fx-min-height: 60;");
                        fields[i][j].setText("");
                    } break;
                    default: {
                        grid(i, j);
                        fields[i][j].setText("");
                    } break;
                }
            }
        }
    }

    /**
     * A tábla bekockásítása. Ha neutrális mező azonosítóval rendelkezik az adott
     * label, akkor a táblában elfoglalt helynek megfelelő színt kap.
     *
     * @param i {@code i} sor index.
     * @param j {@code j} oszlop index.
     */
    private static void grid(int i, int j){
        if(i%2==0){
            if(j%2==0){
                fields[i][j].setStyle("-fx-background-color: #fff6f6;" +
                        "-fx-min-width: 60;-fx-min-height: 60;");
            } else {
                fields[i][j].setStyle("-fx-background-color: #ad1190;" +
                        "-fx-min-width: 60;-fx-min-height: 60;");
            }
        }
        else{
            if(j%2==0){
                fields[i][j].setStyle("-fx-background-color: #ad1190;" +
                        "-fx-min-width: 60;-fx-min-height: 60;");
            } else {
                fields[i][j].setStyle("-fx-background-color: #fff6f6;" +
                        "-fx-min-width: 60;-fx-min-height: 60;");
            }
        }
    }

    /**
     * Újraindul a játék. Törli a tábla összes módosítását és nem nemmódosítja
     * a tárolt adatokat.
     *
     * @param table {@code a tábla amin a módosítások bekövetkeznek.}
     */
    public static void Reset(Table table){
        GameController.Turn = 0;
        TableController tableController = new TableController();
        tableController.create(table);
        TableView.refresh(table);
        Logger.warn("Table refreshed.");
    }
}
