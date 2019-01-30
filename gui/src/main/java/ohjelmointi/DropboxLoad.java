package ohjelmointi;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.files.WriteMode;

/**
 * Allows downloading/loading files to and from dropbox.
 *
 * @author  Samu Koivulahti
 * @version 2018.1129
 * @since   1.8
 */
public class DropboxLoad {

    private static final DbxRequestConfig APP_CONF = new DbxRequestConfig("SamuKoivulahti");
    private String accessToken;
    private DbxClientV2 client;

    /**
    * Uploads the file to dropbox.
    *
    * @param file the file that is going to be uploaded to dropbox
    */
    public void uploadToDropbox(File file) {
        try (FileInputStream input = new FileInputStream(file)) {
            client.files().uploadBuilder("/ShoppingList.json").withMode(WriteMode.OVERWRITE).uploadAndFinish(input);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
    * Verifies that the clients access token is valid.
    */
    private void verifyClientAccessToken() {
        DbxClientV2 client = new DbxClientV2(APP_CONF, accessToken);

        try {
            if (client.users().getCurrentAccount() != null) {
                this.client = client;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
    * Overrides the default constructor.
    */
    public DropboxLoad() {
        try {
            InputStream key = getClass().getResourceAsStream("auth.json");
            DbxWebAuth webAuth = new DbxWebAuth(APP_CONF, DbxAppInfo.Reader.readFully(key));
            DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder().withNoRedirect().build();

            GUI.openResource(webAuth.authorize(webAuthRequest));
            String code = GUI.showTextInput("Dropbox Permission", "Code:");

            if (code != null) {
                DbxAuthFinish authFinish = webAuth.finishFromCode(code);
                accessToken = authFinish.getAccessToken();
            }

            verifyClientAccessToken();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}