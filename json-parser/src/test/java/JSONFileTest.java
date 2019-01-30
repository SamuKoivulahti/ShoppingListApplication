import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.FileWriter;
import org.junit.jupiter.api.Test;
import ohjelmointi.*;

public class JSONFileTest extends Assertions {

    @Test
    public void testJSONReader() {
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

        String path = "target/testShoppingList.JSON";
        
        try(JSONWriter writer = new JSONWriter(new FileWriter(path))) {

        } catch (Exception e) {
            fail("Cannot write to file");
        }
    }
}