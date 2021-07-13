package marvel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marvel.model.character.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
/**
 * MainView class observes the main view through being set as a JavaFX Controller class to (Main.fxml)
 *
 * <p>Takes the view role in MVP pattern as responsible for updating UI component contents</p>
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

    /**
     * A list of ViewObserver objects to be notified of UI events
     */
    List<ViewObserver> observers;

    /**
     * Allow choice of integers to be selected by user for matching index in list
     */
    @FXML
    private ChoiceBox indexList;

    /**
     * Reference to label above choice box in bottom right corner
     */
    @FXML
    private Label listLabel;

    /**
     * Reference to text area for search instructions
     */
    @FXML
    private TextArea searchInstruction;

    /**
     * Reference to text area in lower left panel for displaying searched list of characters
     */
    @FXML
    private TextArea searchedList;

    public MainView() { }

    /**
     * Adds a ViewObserver object to this MainView to receive notification when UI events happen.
     *
     * @param observer Receives notification upon UI events
     */
    public void addObserver(ViewObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    /**
     * Called when Search Character button is clicked.
     *
     * <p>Expects text field to have non empty string value input,
     * will set text area message to ""Input field is empty - enter name for searching." if empty.</p>
     *
     * <p>If text field is non empty, notify all observers event happened</p>
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

    /**
     * Called when the Comics button is clicked
     *
     * <p>Notify all observers event happened</p>
     */
    @FXML
    public void onComics(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onComics();
        }
    }
    /**
     * Called when the Stories button is clicked
     *
     * <p>Notify all observers event happened</p>
     */
    @FXML
    public void onStories(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onStories();
        }
    }

    /**
     * Called when the Events button is clicked
     *
     * <p>Notify all observers event happened</p>
     */
    @FXML
    public void onEvents(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onEvents();
        }
    }

    /**
     * Called when the Series button is clicked
     *
     * <p>Notify all observers event happened</p>
     */
    @FXML
    public void onSeries(){
        //notify observers the event happened
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onSeries();
        }
    }

    /**
     * Called when the URLs button is clicked
     *
     * <p>Notify all observers event happened</p>
     */
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
        if (characterName.getText().equals("") || characterName.getText().isEmpty()) {
            message.setText("Input field is empty - enter name for loading from cache.");
            return;
        }
        //notify observers the event happened
        for(int i = 0 ; i < observers.size() ; i++){
            observers.get(i).onLoadFromCache(characterName.getText());
        }
    }

    /**
     * Sets the lower left text area panel to display text as provided by the String argument result
     *
     * @param result String to be set on display
     */
    public void updateMessage(String result) {
        message.setText(result);
    }

    /**
     * Displays representative thumbnail image of character loaded from URL imagePath<
     *
     *
     * <p>Uses the provided imagePath to create a Image and set it to the upper right ImageView.</p>
     *
     * @param imagePath URL path to a image
     */
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
     * Update TableView to display list of comics which features character
     *
     * @param comics List of Comic objects that contain data of comics featuring current character loaded
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
     * Update center TableView to display list of URLs for public websites tha has character information</p>
     *
     * @param urls List of ResourceUrl objects that contain URLs to public website about character
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
     * Update center TableView to display list of Stories that the character appears
     *
     * @param stories List of Story objects that contain data of stories featuring current character loaded
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
     * Update center TableView to display list of events that the character appears
     *
     * @param events List of Event objects that contain data of events featuring current character loaded
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
     * Update center TableView to display list of series that the character appears
     *
     * @param series List of Series objects that contain data of series featuring current character loaded
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
