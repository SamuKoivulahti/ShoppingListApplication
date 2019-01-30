package ohjelmointi;

/**
 * Parses data and writes it in JSON.
 *
 * @author  Samu Koivulahti
 * @version 2018.1119
 * @since   1.8
 */
public class JSONParser {
    private String input;
    private int position;

    /**
    * Overrides the default constuctor.
    *
    * @param data data to be processed by JSONParser.
    */
    public JSONParser(String data) {
        input = data;
        position = 0;
    }

    /**
    * Parses the data of array.
    *
    * @return parsed data of array
    */
    public JSONArray parseArray() {
        JSONArray array = new JSONArray();
        while (position < input.length()) {
            char now = input.charAt(position++);
            if (now == '{') {
                array.addObject(parseObject());
            } else if (now == ']') {
                break;
            }
        }
        return array;
    }

    /**
    * Parses the data of object.
    *
    * @return parsed data of object
    */
    public JSONObject parseObject() {
        JSONObject object = new JSONObject();
        String key = null;
        while (position < input.length()) {
            char now = input.charAt(position++);
            if (now == '"') {
                if (key == null) {
                    key = parseString();
                    continue;
                } else {
                    object.putString(key, parseString());
                }
                key = null;
            } else if (now >= '0' && now <= '9' && key != null) {
                position--;
                object.putInteger(key, parseInteger());
                key = null;
            } else if (now == '}') {
                break;
            }
        }
        
        return object;
    }

    /**
    * Parses the data of String.
    *
    * @return parsed data of String
    */
    public String parseString() {
        StringBuilder output = new StringBuilder();
        while (position < input.length()) {
            char now = input.charAt(position++);
            if (now == '"') {
                break;
            }
            output.append(now);
        }
        return output.toString();
    }

    /**
    * Parses the data of integer.
    *
    * @return parsed data of integer
    */
    public Integer parseInteger() {
        StringBuilder output = new StringBuilder();
        while (position < input.length()) {
            char now = input.charAt(position++);
            if (!(now >= '0' && now <= '9')) {
                position--;
                break;
            }
            output.append(now);
        }
        return Integer.parseInt(output.toString());
    }

    /**
    * Writes string that contains numbers.
    *
    * @return written value
    */
    public static String writeNumber(int value) {
        return String.valueOf(value);
    }

    /**
    * Writes string that contains characters.
    *
    * @return written value in quotation marks
    */
    public static String writeString(String value) {
        return String.format("\"%s\"", value);
    }

    /**
    * Writes object to JSON.
    *
    * @return complete object in string
    */
    public static String writeObject(JSONObject value) {
        StringBuilder output = new StringBuilder("{");
        boolean isFirst = false;
        for (String key : value.keySet()) {
            if (isFirst) {
                output.append(",");
            }
            
            output.append(writeString(key)).append(":");

            if (value.get(key) instanceof String) {
                output.append(writeString((String)value.get(key)));
            } else {
                output.append(writeNumber((int)value.get(key)));
            }

            isFirst = true;
        }

        return output.append("}").toString();
    }

    /**
    * Writes array to JSON.
    *
    * @return complete JSONArray
    */
    public static String writeArray(JSONArray array) {
        StringBuilder output = new StringBuilder("[");
        for (int i = 0; i < array.size(); i++) {
            if (i > 0) {
                output.append(",");
            }
            output.append(writeObject(array.get(i)));
        }

        return output.append("]").toString();
    }
}