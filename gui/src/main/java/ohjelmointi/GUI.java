package ohjelmointi;

import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.geometry.*;
import javafx.scene.layout.*;

import java.awt.TextField;
import java.util.*;

import javax.persistence.criteria.Selection;

import org.hibernate.annotations.OnDeleteAction;

import javafx.scene.control.TableColumn;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.util.converter.*;
import ohjelmointi.mapping.JSONMapper;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.stage.FileChooser;
import javafx.scene.control.*;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.net.URI;

/**
 * Creates GUI for application.
 *
 * @author  Samu Koivulahti
 * @version 2018.1128
 * @since   1.8
 */
public class GUI extends Application {
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> item;
    @FXML
    private TableColumn<Item, Integer> amount;

    private static FileChooser.ExtensionFilter FILTER = new FileChooser.ExtensionFilter("JSON Files", "*.json");

    private Stage stage;

    private ObservableList<Item> properties;

    private File file;



   /**
    * Launches program.
    *
    * @param  args not used
    */
    public static void main(String[] args) {
        System.out.println("Author: Samu Koivulahti");
        launch(args);
    }

   /**
    * Creates initial memory database configurations when started up.
    */
    @Override
    public void init() {
        DatabaseLoad.create();
    }

    /**
    * Calls the delete method from DatabaseLoad, closes session factory.
    */
    @Override
    public void stop() {
        DatabaseLoad.delete();
    }

    /**
     * @see Application#start(Stage) start
     */
    @Override
    public void start(Stage stage) {
        try {

            this.stage = stage;
            stage.setMinWidth(800);
            stage.setMinHeight(800);
            stage.setScene(createFXMLScene());
            stage.setTitle("ShoppingListApplication");
            stage.setOnCloseRequest(e -> Platform.exit());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    /**
    * Creates a scene from FXML.
    */
    private Scene createFXMLScene() throws Exception {
        return new Scene(FXMLLoader.load(getClass().getResource("GUI.fxml")));
    }

    /**
    * Prosesses FXML event when closed.
    */
    @FXML
    private void onCloseAction() {
        Platform.exit();
    }

    /**
    * Prosesses FXML event when new list is created.
    */
    @FXML
    private void onCreateItemAction() {
        properties.add(new Item("New Item", 0));
        tableView.layout();
        tableView.getSelectionModel().select(properties.size() -1);
        tableView.edit(properties.size() - 1, item);
    }

    /**
    * Prosesses FXML event when item is deleted.
    */
    @FXML
    private void onDeleteItemAction() {
        Item selected = tableView.getSelectionModel().getSelectedItem();
        properties.remove(selected);
    }

    /**
    * Prosesses FXML event when list is saved.
    */
    @FXML
    private void onSaveAction() {
        saveToJSON();
    }

    /**
    * Prosesses FXML event when item is edited.
    */
    @FXML
    public void onEditItemAction() {
        TablePosition pos = tableView.getFocusModel().getFocusedCell();
        if (tableView.getEditingCell() != null && tableView.getEditingCell().getColumn() == 0) {
            tableView.edit(pos.getRow(), tableView.getColumns().get(1));
        } else {
            tableView.edit(pos.getRow(), tableView.getColumns().get(0));
        }
    }

    /**
    * Prosesses FXML event when specific file is loaded.
    */
    @FXML
    private void onLoadFileAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose list");
        fileChooser.getExtensionFilters().add(FILTER);

        File loadFile = fileChooser.showOpenDialog(stage);

        if (loadFile != null) {
            this.file = loadFile;
            tableView.getItems().clear();
            tableView.getItems().addAll(loadFromJSON());
        }
        tableView.refresh();
    }

    /**
    * Prosesses FXML event when list is uploaded to dropbox.
    */
    @FXML
    private void onUploadToDropboxAction() {
        DropboxLoad dropbox = new DropboxLoad();
        try {
            saveToJSON();
            dropbox.uploadToDropbox(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
    * Prosesses FXML event when list is saved to database.
    */
    @FXML
    private void onSaveToDatabaseAction() {
        DatabaseLoad.addItems(properties);
    }

    /**
    * Prosesses FXML event when list is uploaded from database.
    */
    @FXML
    private void onLoadFromDatabaseAction() {
        properties = DatabaseLoad.getItems();
        tableView.setItems(properties);        
    }

    /**
    * Prosesses FXML event when item is deleted from menubar.
    */
    @FXML
    private void onDeleteAction() {
        onDeleteItemAction();
    }

    /**
    * Prosesses FXML event when item is created from menubar.
    */
    @FXML
    private void onNewAction() {
        onCreateItemAction();
    }

    /**
    * Prosesses FXML event when item is saved to specific location with specific name.
    */
    @FXML
    private void onSaveAsAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save list");
        fileChooser.getExtensionFilters().add(FILTER);

        File saveFile = fileChooser.showSaveDialog(stage);

        if (saveFile != null) {
            this.file = saveFile;
            onSaveAction();
        }
    }

    /**
    * Prosesses FXML event when item is edited.
    */
    @FXML
    private void onEditCommitAction(CellEditEvent<Item, ?> event) {

        Object value = event.getNewValue();
        Item selected = tableView.getSelectionModel().getSelectedItem();
        if (value instanceof Integer) {
            selected.setItemAmount((Integer)value);
        } else if (value instanceof String) {
            selected.setItemName((String)value);
        }
    }

    /**
    * Prosesses FXML event when about button is pushed from menubar.
    */
    @FXML
    public void onAboutAction() {
        Alert alert = new Alert(AlertType.INFORMATION, null);
        alert.setTitle("About");
        alert.setContentText("Shopping list application by Samu Koivulahti");
        alert.setHeaderText(null);
        alert.show();
    }

   /**
    * Initializes FXML.
    */
    @FXML
    private void initialize() {
        file = new File("ShoppingList.json");
        properties = loadFromJSON();
        item.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        item.setCellFactory(TextFieldTableCell.forTableColumn());
        item.prefWidthProperty().bind(tableView.widthProperty().subtract(amount.getWidth() + 2));
        amount.setCellValueFactory(new PropertyValueFactory<>("itemAmount"));
        amount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableView.setItems(properties);
        tableView.setEditable(true);
    }

    /**
    * Saves the list to file.
    */
    private void saveToJSON() {
        try(JSONWriter writer = new JSONWriter(new FileWriter(file))) {
            JSONArray array = new JSONArray();
            for (Item i : properties) {
                array.addObject(JSONMapper.saveMapping(i));
            }

            writer.writeJSONArray(array);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    /**
    * Loads list from file.
    *
    * @return returns the data from list
    */
    private ObservableList<Item> loadFromJSON() {
        ObservableList<Item> list = FXCollections.observableArrayList();
        try(JSONReader reader = new JSONReader(new FileReader(file))) {
            for (JSONObject object : reader.readJSONArray()) {
                Item item = new Item();
                list.add(JSONMapper.loadMapping(item, object));
                item.updateProperties();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
    * Opens url resources.
    */
    public static void openResource(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
    * Sets texts for messagewindow.
    *
    * @param title gets title for messagewindow
    * @param message gets the message for messagewindow
    */
    public static String showTextInput(String title, String message) {
        TextInputDialog input = new TextInputDialog();

        input.setContentText(message);
        input.setHeaderText(null);
        input.setTitle(title);

        Optional<String> result = input.showAndWait();
        return result.isPresent() ? result.get() : null;
    }
}