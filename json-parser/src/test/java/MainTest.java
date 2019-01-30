import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ohjelmointi.*;

public class MainTest {
    @Test
    public void mainTest() {
        try (BufferedReader reader = new BufferedReader(new FileReader("shoppingList.JSON"))) {
            StringBuilder string = new StringBuilder();
            String line = null;
        
            while ((line = reader.readLine()) != null) {
                string.append(line);
            }
        
            JSONParser parser = new JSONParser(string.toString());
            JSONArray array = parser.parseArray();
        
            for (Object value : array) {
                if (value instanceof JSONObject) {
                    JSONObject object = (JSONObject) value;
                    System.out.println(object);

                }
            }

            try (JSONWriter writer = new JSONWriter(new FileWriter("shoppinListTest.JSON"))) {
                JSONObject object = new JSONObject();
                object.putString("Item", "Orange");
                object.putInteger("Amount", 3);
                array.addObject(object);
                writer.writeJSONArray(array);
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}