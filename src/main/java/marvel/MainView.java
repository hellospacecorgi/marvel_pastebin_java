package marvel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvel.model.character.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
/**
 * MainView class observes the main view (Main.fxml)
 *
 * <p>Listens for user events on UI and notify ViewObservers upon user events,
 * update view components via manipulating FXML attributes.</p>
 *
 * <p>Used by MainPresenter for updated data presentation</p>
 *
 * @see ViewObserver
 * @version 1.0.0
 */
public class MainView {
    /**
     * Takes in user input to generate search enquiry for character information.
     */
    @FXML
    TextField characterName;

    /**
     * Lower left panel where response messages are printed.
     * Response messages can be request results (character information, link to output report paste), error messages, instructions
     */
    @FXML
    TextArea message;

    /**
     * Table for displaying lists of resources for character
     */
    @FXML
    TableView centerTable;

    /**
     * Displays thumbnail image of character
     */
    @FXML
    ImageView thumbnail;

    List<ViewObserver> observers;

    public MainView() { }

    public void addObserver(ViewObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    /**
     * Called when Search Character button is clicked.
     *
     * <p>Notify all observers event happened</p>
     *
     * <p>Expects text field to have non empty string value input.</p>
     */
    @FXML
    public void onSearch() {
        if (characterName.getText().equals("") || characterName.getText().isEmpty()) {
            message.setText("Input field is empty - enter name for searching.");
            return;
        }

        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onSearch(characterName.getText());
        }
    }

    @FXML
    public void onComics(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onComics();
        }
    }

    @FXML
    public void onStories(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onStories();
        }
    }

    @FXML
    public void onEvents(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onEvents();
        }
    }

    @FXML
    public void onSeries(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onSeries();
        }
    }

    @FXML
    public void onUrl(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onUrls();
        }
    }

    /**
     * Called when the Send Report button is clicked.
     *
     * <p>Notify all observers event happened</p>
     */
    @FXML
    public void onSendReport(){
        //notify observers the event happened
        for(int i = 0 ; i < observers.size() ; i++){
            observers.get(i).onSendReport();
        }
    }

    /**
     * Called when the Load from cache button is clicked
     *
     * <p>Notify all observers event happened</p>
     *
     * <p>Expects text field to be non empty when button clicked</p>
     */
    @FXML
    public void onLoadFromCache(){
        //notify observers the event happened
        for(int i = 0 ; i < observers.size() ; i++){
            observers.get(i).onLoadFromCache(characterName.getText());
        }
    }

    public void updateMessage(String result) {
        message.setText(result);
    }

    public void updateThumbnail(String imagePath) {
        //Update thumbnail view
        Image img = null;
        if (imagePath != null) {
            img = new Image(imagePath);
            thumbnail.setImage(img);
        } else {
            try {
                img = new Image(new FileInputStream("./src/main/resources/marvel/dummy.png"));
                thumbnail.setImage(img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Update the center table TableView to display list of comics which features character
     *
     * @param comics
     */
    public void updateTableViewComics(List<Comic> comics) {
        ObservableList<Comic> clist = FXCollections.observableArrayList(comics);

        TableColumn<ResourceUrl, String> name = new TableColumn<>("Comic Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ResourceUrl, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("resourcePath"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(clist);
        centerTable.getColumns().addAll(name, path);
    }

    /**
     * Called when the Url button above the table is clicked.
     *
     * <p>Displays list of URLs for public websites tha has character information</p>
     */
    public void updateTableViewResourceUrls(List<ResourceUrl> urls) {
        ObservableList<ResourceUrl> rlist = FXCollections.observableArrayList(urls);

        TableColumn<ResourceUrl, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<ResourceUrl, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("url"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(rlist);
        centerTable.getColumns().addAll(type, path);

    }

    /**
     * Called when the Stories button above the table is clicked.
     *
     * <p>Displays list of Stories that the character appears</p>
     */
    public void updateTableViewStories(List<Story> stories) {
        ObservableList<Story> clist = FXCollections.observableArrayList(stories);

        TableColumn<Story, String> name = new TableColumn<>("Story Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Story, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Story, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("resourcePath"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(clist);
        centerTable.getColumns().addAll(name, type, path);
    }

    /**
     * Called when the Events button above the table is clicked.
     *
     * <p>Displays list of events that the character appears</p>
     */
    public void updateTableViewEvents(List<Event> events) {
        ObservableList<Event> clist = FXCollections.observableArrayList(events);

        TableColumn<Event, String> name = new TableColumn<>("Event Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Event, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("resourcePath"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(clist);
        centerTable.getColumns().addAll(name, path);
    }

    /**
     * Called when the Series button above the table is clicked.
     *
     * <p>Displays list of series that the character appears</p>
     */
    public void updateTableViewSeries(List<Series> series) {
        ObservableList<Series> clist = FXCollections.observableArrayList(series);

        TableColumn<Series, String> name = new TableColumn<>("Comic Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Series, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("resourcePath"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(clist);
        centerTable.getColumns().addAll(name, path);
    }

}
