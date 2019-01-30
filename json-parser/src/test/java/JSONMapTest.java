import org.junit.jupiter.api.Assertions;

import java.io.FileWriter;
import org.junit.jupiter.api.Test;
import ohjelmointi.*;
import ohjelmointi.mapping.*;

public class JSONMapTest extends Assertions {

    @Test
    public void testJSONMapper() {
        Item item = new Item();
        JSONObject object = new JSONObject();
        object.putInteger("amount", 5);
        JSONMapper.loadMapping(item, object);
        assertEquals((Integer)5, item.amount);
    }
}

@JSONMap
class Item {

    @JSONMap
    public Integer amount;
}