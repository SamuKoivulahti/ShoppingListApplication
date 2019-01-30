package ohjelmointi;

import java.util.HashMap;

/**
 * Stores JSONObject information.
 *
 * @author  Samu Koivulahti
 * @version 2018.1119
 * @since   1.8
 */
public class JSONObject extends HashMap<String, Object> {

    /**
    * Adds string value.
    *
    * @param key id
    * @param value string value
    */
    public void putString(String key, String value) {
        put(key, value);
    }

    /**
    * Gets key in string format.
    *
    * @param key id
    * @return key in string format
    */
    public String getString(String key) {
        return (String)get(key);
    } 

    /**
    * Adds integer value.
    *
    * @param key id
    * @param value integer value
    */
    public void putInteger(String key, Integer value) {
        put(key, value);
    }

    /**
    * Gets key in Integer format.
    *
    * @param key id
    * @return key in integer format
    */
    public Integer getInteger(String key) {
        return (Integer)get(key);
    }
}