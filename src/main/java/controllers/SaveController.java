package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import models.Save;
import models.Table;
import org.pmw.tinylog.Logger;
import views.TableView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A játék állásának lementése. Ha egy új mentést készítünk
 * hozzáírjuk az újat a többihez, amihez egy id-n keresztül lehet betölteni.
 */
public class SaveController {

    /**
     * A mentések a {@value #SAVES}-ben található.
     */
    private static final String SAVES  = "saves.json";

    /**
     * Konténer az elemek tárolásához.
     */
    private List<Save> saves;

    /**
     * Új elem létrehozásánál egy új olvasási folyamatott indítunk.
     */
    public SaveController(){
        this(SaveController.class.getResourceAsStream(SAVES));
    }

    /**
     * A {@link #SAVES} json-ból kiolvassa az adatokat.
     *
     * @param is {@code is} új folyam a {@value #SAVES} eléréséhez.
     */
    private SaveController(InputStream is){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Save>>(){}.getType();
        this.saves =gson.fromJson(new InputStreamReader(is),type);
    }

    /**
     * Visszadja a {@link #saves}-ban tárált adatokat.
     *
     * @return visszaadja az összes mentést {@value #SAVES}-ből.
     */
    public List<Save> getSaves() {
        return saves;
    }

    /**
     * Egy új konténert tartalmával cseréljük a meglévőnek
     * a tartalmát  lsd. {@link #Add(Save)}.
     *
     * @param saves {@code saves} új konténer.
     */
    private void setSaves(List<Save> saves) {
        this.saves = saves;
    }

    /**
     * Új elem hozzáadása a listához. új eleme elsőnek egy ideiglenes
     * tárolóba kerül majd onnan felülírással a {@link #SAVES}-ba.
     *
     * @param save {@code saves} új elem, ami hozzáadásra kerül.
     */
    public void Add(Save save){
        List<Save> tmp_player = this.getSaves();
        tmp_player.add(save);
        this.setSaves(tmp_player);
        try {
            Gson gson = new Gson();
            JsonWriter writer = new JsonWriter(
                    new FileWriter("target/classes/controllers/" + SAVES));
            writer.setIndent("  ");
            writer.beginArray();
            this.getSaves().stream()
                    .forEach(element -> gson.toJson(element, Save.class ,writer));
            writer.endArray();
            writer.close();
            Logger.info("Data has rewritten.");
        } catch (IOException e){
            Logger.error("IO Exception:\t {}", e.getCause());
        }
        Logger.info("New element added to {}", SAVES);
    }

    /**
     * Visszaadja egy mentést az id-n keresztül. ha az id nem megfelelő
     * null értéket add vissza.
     *
     * @param s {@code s} id amin keresztül hivatkozunk a mentésre.
     * @return visszaadd egy mentést id alapján.
     */
    public Save getSaveById(String s){
        List<Save> tmp_saves = (ArrayList) this.getSaves();
        Save save = tmp_saves.stream()
                .filter(sv -> sv.getId().equals(s))
                .findFirst()
                .orElse(null);

        if(save == null){
            Logger.warn("Save not found!");
            return null;
        }
        else {
            return save;
        }
    }

    /**
     * Betöltés után felülírja az állást a betöltött játék értékeviel. Majd
     * refresh-eli a table-t.
     *
     * @param name {@code name} input ablakon beírt azonososító ami már le lett tesztelve
     *                         a validtiása.
     * @param table {@code table} a tábla ami firssítve lesz.
     */
    public void Load(String name, Table table){
        SaveController saveController = new SaveController();
        Save save = saveController.getSaveById(name);
        GameController.Turn = save.getTurn();
        DataController.Session = save.getGame();
        table.setTable(save.getTable());
        TableView.refresh(table);
    }
}
