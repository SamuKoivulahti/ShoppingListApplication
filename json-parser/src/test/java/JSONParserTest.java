import org.junit.jupiter.api.Assertions;

import java.io.FileWriter;
import org.junit.jupiter.api.Test;
import ohjelmointi.*;

public class JSONParserTest extends Assertions {

    @Test
    public void testParseArray() {
        String objectA = "[{\"item\":\"product\"},{\"amount\":\"0\"}]";
        JSONArray array = new JSONArray();
        JSONObject objectB = new JSONObject();
        objectB.putString("item", "product");
        array.addObject(objectB);
        objectB = new JSONObject();
        objectB.putString("amount", "0");
        array.addObject(objectB);
        JSONParser parser = new JSONParser(objectA);
        JSONArray result = parser.parseArray();
        assertEquals(array, result);
        assertNotSame(array, result);
    }

    @Test
    public void testFailParse() {
        String objectA = "[{\"item\":\"product\"},{\"amount\":0.5}]";
        JSONArray array = new JSONArray();
        JSONObject objectB = new JSONObject();
        objectB.putString("item", "product");
        array.addObject(objectB);
        objectB = new JSONObject();
        objectB.putInteger("amount", 5);
        array.addObject(objectB);
        JSONParser parser = new JSONParser(objectA);
        JSONArray result = parser.parseArray();
        assertNotEquals(array, result);
        assertNotSame(array, result);
    }
}