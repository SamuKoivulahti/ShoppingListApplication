package ohjelmointi;

import java.io.IOException;
import java.io.Reader;

/**
 * Reads JSON files.
 *
 * @author  Samu Koivulahti
 * @version 2018.1119
 * @since   1.8
 */
public class JSONReader implements AutoCloseable {
    private Reader reader;

    /**
    * Overrides default constructor.
    *
    * @param reader object to read data from
    */
    public JSONReader(Reader reader) {
        this.reader = reader;
    }

    /**
    * Reads JSONArray.
    *
    * @return contents in JSONArray fromat.
    */
    public JSONArray readJSONArray() throws IOException {
        StringBuilder contents = new StringBuilder();
        int character = -1;
        while ((character = reader.read()) != -1) {
            contents.append((char)character);
        }

        return new JSONParser(contents.toString()).parseArray();
    }

    /**
    * Closes the reader.
    */
    @Override
    public void close() throws Exception {
        if (reader != null) {
            reader.close();
        }
    }
}