package ohjelmointi;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes JSON files.
 *
 * @author  Samu Koivulahti
 * @version 2018.1119
 * @since   1.8
 */
public class JSONWriter implements AutoCloseable {
    private Writer writer;

    /**
    * Overrides the default constructor
    *
    * @param writer object to write data to
    */
    public JSONWriter(Writer writer) {
        this.writer = writer;
    }

    /**
    * Writes JSONArray.
    *
    * @param array data to write
    */
    public void writeJSONArray(JSONArray array) throws IOException {
        writer.write(JSONParser.writeArray(array));
    }

    /**
    * Closes writer.
    */
    @Override
    public void close() throws Exception {
        if (writer != null) {
            writer.close();
        }
    }
}