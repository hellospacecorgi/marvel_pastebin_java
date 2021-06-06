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
import marvel.model.ModelObserver;
import marvel.model.character.*;
import marvel.model.ModelFacade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * MainPresenter class observes the main view (Main.fxml),
 * triggers Model operations upon user events,
 * and ask corresponding view components to update accordingly via manipulating FXML attributes.
 *
 * @version 1.0.0
 * @see View
 * @see ViewSwitcher
 */
public class MainPresenter implements ModelObserver {
    /**
     *  Takes in user input to generate search enquiry for character information.
     */
    @FXML
    TextField characterName;

    /**
     *  Represents the lower left panel where response messages are printed.
     *  Response messages can be request results (character information, link to output report paste), error messages, instructions
     */
    @FXML
    TextArea message;

    /**
     *
     */
    @FXML
    TableView centerTable;

    /**
     *
     */
    @FXML
    ImageView thumbnail;

    /**
     *
     */
    ModelFacade model;

    public MainPresenter(ModelFacade model){
        this.model = model;
        //adds itself to be an observer
        model.addObserver(this);
    }

    /**
     * Triggered by 'Search Character' button.
     * Expects textfield to have string value input.
     *
     */
    @FXML
    public void onSearch() {
        if(characterName.getText().equals("") || characterName.getText().isEmpty()){
            message.setText("Input field is empty - enter name for searching.");
            return;
        }

        //Ask model to process request
        model.getCharacterInfo(characterName.getText());


    }

    @FXML
    public void onComics(){
        updateCenterComics();
    }

    public void updateCenterComics(){
        ObservableList<Comic> clist = FXCollections.observableArrayList(model.getCurrentCharacter().getComicList());

        TableColumn<ResourceUrl, String> name = new TableColumn<>("Comic Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ResourceUrl, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("resourcePath"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(clist);
        centerTable.getColumns().addAll(name, path);

    }


    @FXML
    public void onUrl(){
        ObservableList<ResourceUrl> rlist = FXCollections.observableArrayList(model.getCurrentCharacter().getUrls());

        TableColumn<ResourceUrl, String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<ResourceUrl, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("url"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(rlist);
        centerTable.getColumns().addAll(type, path);

    }

    @FXML
    public void onStories(){
        ObservableList<Story> clist = FXCollections.observableArrayList(model.getCurrentCharacter().getStoryList());

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

    @FXML
    public void onEvents(){
        ObservableList<Event> clist = FXCollections.observableArrayList(model.getCurrentCharacter().getEventList());

        TableColumn<Event, String> name = new TableColumn<>("Event Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Event, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("resourcePath"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(clist);
        centerTable.getColumns().addAll(name, path);
    }

    @FXML
    public void onSeries(){
        ObservableList<Series> clist = FXCollections.observableArrayList(model.getCurrentCharacter().getSeriesList());

        TableColumn<Series, String> name = new TableColumn<>("Comic Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Series, String> path = new TableColumn<>("URL Path");
        path.setCellValueFactory(new PropertyValueFactory<>("resourcePath"));

        centerTable.getItems().clear();
        centerTable.getColumns().clear();
        centerTable.setItems(clist);
        centerTable.getColumns().addAll(name, path);
    }

    @FXML
    public void onSendReport(){
        model.sendReport(model.getCurrentCharacter());


    }

    @Override
    public void updateCharacterInfo() {

        //Retrieve updated data in CharacterInfo
        CharacterInfo info = model.getCurrentCharacter();
        if(info == null){
            message.setText("Search returned no character information with provided name.");
            return;
        }
        String result = "Character Summary \n";
        result = result.concat("ID: ").concat(String.valueOf(info.getId()));
        result = result.concat("\nName: ").concat(info.getName());
        result = result.concat("\nDescription: ").concat(info.getDescription());
        result = result.concat("\nNumber of Comics: ").concat(String.valueOf(info.getNComics()));
        result = result.concat("\nNumber of Stories: ").concat(String.valueOf(info.getNStories()));
        result = result.concat("\nNumber of Events: ").concat(String.valueOf(info.getNEvents()));
        result = result.concat("\nNumber of Series: ").concat(String.valueOf(info.getNSeries()));
        result = result.concat("\nLast modified: ").concat(info.getModified());
        message.setText(result);

        //Update view with response
        updateCenterComics(); //show list of comics by default

        //Update thumbnail view
        Image img = null;
        if(model.getImagePathByInfo(info) != null){
            img = new Image(model.getImagePathByInfo(info));
            thumbnail.setImage(img);
        } else {
            try{
                img = new Image(new FileInputStream("./src/main/resources/marvel/dummy.png"));
                thumbnail.setImage(img);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateReportUrl() {
        String url = model.getReportUrl();
        if(url != null){
            message.setText("Output Report URL: " + model.getReportUrl());
        } else {
            message.setText("Failed sending report - check if last search is valid and loaded results.");
        }
    }
}
