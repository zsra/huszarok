package controllers;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import interfaces.IIOController;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * IO vezérlők.
 */
public class IOController implements IIOController {

    /**
     * A paraméterben kapott helyen kiolvassa az összes megtalálható elemet az aktuális
     * Json-ben.
     *
     * @param typekey Az aktuálisan olvasott elemek osztálya.
     * @param filename a json neve ahonnan olvas.
     * @param <T> a kapott lista elmeinek típusa.
     * @return Visszaadja a kiolvasott elemeket.
     * @throws IOException Ha nem létezik ez a File IOException dob.
     */
    public <T> List<T> GetAll(Class<?> typekey , String filename) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(
                new InputStreamReader(PlayerController
                        .class.getResourceAsStream(filename), "UTF-8"));
        List<T> elements = new ArrayList<>();
        reader.beginArray();
        while(reader.hasNext()){
            T element = gson.fromJson(reader, typekey);
            elements.add(element);
        }
        reader.endArray();
        reader.close();
        return elements;
    }

    /**
     * Új elem hozzáadása a megfelelő Json-hoz.
     *
     * @param element a kiírni kívánt elem.
     * @param filename a json neve ahova ír.
     * @param elements Az elemek listája amivel együtt ki lesz írva.
     * @param typekey Az aktuálisan kiírt elemek osztálya.
     * @param <T> a kiírandó elem típusa.
     * @throws IOException Ha nem létezik ez a File IOException dob.
     */
    public <T> void New(T element, String filename, List<T> elements, Class<?> typekey) throws IOException {
        List<T> tmp = elements;
        tmp.add(element);
        WriteToJson(tmp, filename, typekey);
    }

    /**
     * Az írás metóduda. A kapott listával felülírja a Json-t.
     *
     * @param tmp A lista aminek az elemeivel felül lesz írva az állomány.
     * @param filename a json neve ahova ír.
     * @param typekey Az aktuálisan kiírt elemeknek osztálya.
     * @param <T> a kiírandó elemek típusa.
     * @throws IOException Ha nem létezik ez a File IOException dob.
     */
    public <T> void WriteToJson(List<T> tmp, String filename, Class<?> typekey) throws IOException {
        Gson gson = new Gson();
        JsonWriter writer = new JsonWriter(
                new FileWriter("target/classes/controllers/" + filename));
        writer.setIndent("  ");
        writer.beginArray();
        tmp.stream()
                .forEach(element -> gson.toJson(element, typekey, writer));
        writer.endArray();
        writer.close();
    }
}
