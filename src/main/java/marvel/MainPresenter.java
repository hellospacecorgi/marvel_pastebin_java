package marvel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import marvel.model.character.*;
import marvel.model.ModelFacade;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * MainPresenter class observes the main view (Main.fxml),
 * triggers Model operations upon user events,
 * and ask corresponding view components to update accordingly via manipulating FXML attributes.
 *
 * @version 1.0.0
 * @see View
 * @see ViewSwitcher
 */
public class MainPresenter {
    /**
     *  Takes in user input to generate search enquiry for character information.
     *  Expects string input.
     */
    @FXML
    TextField characterName;

    /**
     *  Represents the lower left panel where response messages are printed.
     */
    @FXML
    TextArea message;

    @FXML
    TableView centerTable;

    @FXML
    ImageView thumbnail;

    ModelFacade model;

    public MainPresenter(ModelFacade model){

        this.model = model;

    }

    /**
     * Triggered by 'Search Character' button.
     * Expects textfield to have string value input.
     * @throws IOException
     */
    @FXML
    public void onSearch() throws IOException {
        if(characterName.getText().equals("") || characterName.getText().isEmpty()){
            message.setText("Input field is empty - enter name for searching.");
            return;
        }

        //Retrieve response from model
        CharacterInfo info = model.getCharacterInfo(characterName.getText());
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

        thumbnail.setImage(model.getImageByInfo(info));

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
        boolean status = model.sendReport(model.getCurrentCharacter());
        if(status == true){
            message.setText("Output Report URL: " + model.getReportUrl());
        } else {
            message.setText("Failed sending report - check if loaded one character information already?");
        }

    }
}
