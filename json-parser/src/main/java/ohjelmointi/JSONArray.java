package ohjelmointi;

import java.util.ArrayList;

/**
 * Stores JSONArray information.
 *
 * @author  Samu Koivulahti
 * @version 2018.1119
 * @since   1.8
 */
public class JSONArray extends ArrayList<JSONObject> {

    /**
    * Adds object value.
    *
    * @param object JSONObject value
    */
    public void addObject(JSONObject object) {
        add(object);
    }
}
