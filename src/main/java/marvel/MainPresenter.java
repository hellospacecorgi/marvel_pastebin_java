package marvel;

import marvel.model.ModelObserver;
import marvel.model.character.*;
import marvel.model.ModelFacade;

/**
 * MainPresenter class observes the main view (Main.fxml)
 *
 * <p>Triggers ModelFacade operations upon user events,
 * and ask corresponding view components to update accordingly via manipulating FXML attributes.</p>
 *
 * <p>Listens for notification from ModelFacade as a ModelObserver to perform respective update operations</p>
 *
 * @version 1.0.0
 */
public class MainPresenter implements ModelObserver, ViewObserver {

    /**
     * Reference to model for interaction with the input and output APIs
     */
    ModelFacade model;
    MainView view;

    /**
     * Takes in a ModelFacade object with non null InputModel and OutputModel,
     * takes in a MainView object,
     * and adds itself to the model and view's observer list.
     *
     * @param model Interface for retrieving and posting data on character information
     * @param view Manages UI components and display contents
     */
    public MainPresenter(ModelFacade model, MainView view){
        this.model = model;
        this.view = view;
        //adds itself to be an observer
        model.addObserver(this);
        view.addObserver(this);
    }
    /**
     * Called when Search Character button is clicked.
     *
     * <p>Asks model to process searching for character information given String name</p>
     *
     */
    @Override
    public void onSearch(String name){
        //Ask model to process request
        model.getCharacterInfo(name);
    }

    /**
     * Called when the Comic button above the table is clicked.
     *
     * <p>Calls it's own MainView's updateCenterComics() to populate the table.</p>
     */
    public void onComics(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewComics(model.getCurrentCharacter().getComicList());
        }

    }

    /**
     * Called when the Url button above the table is clicked.
     *
     * <p>Displays list of URLs for public websites tha has character information</p>
     */
    public void onUrl(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewResourceUrls(model.getCurrentCharacter().getUrls());
        }
    }

    /**
     * Called when the Stories button above the table is clicked.
     *
     * <p>Displays list of Stories that the character appears</p>
     */
    public void onStories(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewStories(model.getCurrentCharacter().getStoryList());
        }
    }
    /**
     * Called when the Events button above the table is clicked.
     *
     * <p>Displays list of events that the character appears</p>
     */
    public void onEvents(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewEvents(model.getCurrentCharacter().getEventList());
        }
    }
    /**
     * Called when the Series button above the table is clicked.
     *
     * <p>Displays list of series that the character appears</p>
     */
    public void onSeries(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewSeries(model.getCurrentCharacter().getSeriesList());
        }
    }

    @Override
    public void onUrls() {
        if(model.getCurrentCharacter() != null){
            view.updateTableViewResourceUrls(model.getCurrentCharacter().getUrls());
        }
    }

    /**
     * Called when the Send Report button is clicked.
     *
     * <p>Ask model to generate report based on character data, send report to Pastebin to create new paste</p>
     */
    @Override
    public void onSendReport(){
        if(model.getCurrentCharacter() != null){
            model.sendReport(model.getCurrentCharacter());
        }
    }
    /**
     * Performs action upon knowing CharacterInfo is built in model
     *
     * <p>Display character summary on lower left text area,
     * display list of comics in table by default (calling updateCenterComic() )
     * display thumbnail image of character in top right ImageView</p>
     */
    @Override
    public void updateCharacterInfo() {
        //Retrieve updated data in CharacterInfo
        CharacterInfo info = model.getCurrentCharacter();
        if(info == null){
            view.updateMessage("Search returned no character information with provided name.");
            return;
        }
        String result = "Character Summary \n";
        result = result.concat("ID: ").concat(String.valueOf(info.getId()));
        result = result.concat("\nName: ").concat(info.getName());
        result = result.concat("\nDescription: ").concat(info.getDescription());
        result = result.concat("\n\nNumber of Comics: ").concat(String.valueOf(info.getNComics()));
        result = result.concat("\nNumber of Stories: ").concat(String.valueOf(info.getNStories()));
        result = result.concat("\nNumber of Events: ").concat(String.valueOf(info.getNEvents()));
        result = result.concat("\nNumber of Series: ").concat(String.valueOf(info.getNSeries()));
        result = result.concat("\nLast modified: ").concat(info.getModified());

        view.updateMessage(result);

        //Update view with response
        view.updateTableViewComics(model.getCurrentCharacter().getComicList()); //show list of comics by default
        view.updateThumbnail(model.getImagePathByInfo(info));

    }
    /**
     * Performs action upon knowing paste report URL is obtained in model
     *
     * <p>Display paste report URL in lower left text area</p>
     */
    @Override
    public void updateReportUrl() {
        String url = model.getReportUrl();
        if(url != null){
            view.updateMessage("Output Report URL: " + model.getReportUrl());
        } else {
            view.updateMessage("Failed sending report - check if last search is valid and loaded results.");
        }
    }
}
