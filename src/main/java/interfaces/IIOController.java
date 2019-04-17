package interfaces;

import java.io.IOException;
import java.util.List;

/**
 * IO kezelő függvények.
 */
public interface IIOController {

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
    <T> List<T> GetAll(Class<?> typekey, String filename) throws IOException;

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
    <T> void New(T element, String filename, List<T> elements, Class<?> typekey) throws IOException;

    /**
     * Az írás metóduda. A kapott listával felülírja a Json-t.
     *
     * @param tmp A lista aminek az elemeivel felül lesz írva az álomány.
     * @param filename a json neve ahova ír.
     * @param typekey Az aktuálisan kiírt elemeknek osztálya.
     * @param <T> a kiírandó elemek típusa.
     * @throws IOException Ha nem létezik ez a File IOException dob.
     */
    <T> void WriteToJson(List<T> tmp, String filename, Class<?> typekey) throws IOException;
}
