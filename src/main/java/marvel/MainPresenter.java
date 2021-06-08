package marvel;

import marvel.model.ModelObserver;
import marvel.model.character.*;
import marvel.model.ModelFacade;

/**
 * MainPresenter class that observes the ModelFacade and MainView object that is passed in through the constructor.
 *
 * <p>Calls operations upon notification from MainView on user events,
 * retrieves data from the model and ask the view to update accordingly.</p>
 *
 * <p>Listens for user events from the MainView. Listens for requests completion from the ModelFacade</p>
 *
 * @see ModelObserver
 * @see ModelFacade
 * @see MainView
 * @see ViewObserver
 * @version 1.2.0
 */
public class MainPresenter implements ModelObserver, ViewObserver {

    /**
     * Reference to model for interaction with the input and output APIs
     */
    ModelFacade model;

    /**
     * Reference to view for updates on data requests.
     */
    MainView view;

    /**
     * Used for tracking if search is clicked twice on same name.
     *
     * <p>Doing so confirms to retrieve information from API instead of cache.</p>
     */
    String lastSearched = "";

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
     * Notified when Search Character button is clicked.
     *
     * <p>Asks model to process searching for character information given String name</p>
     */
    @Override
    public void onSearch(String name){
        if(lastSearched.equals(name)){
            //ask model to get info from API
            model.getCharacterInfo(name);
            return;
        }

        //Check if there is cached data in database
        if(model.isInfoInCache(name)){
            //If cache exists - ask user for option to load from API or cache
            view.updateMessage("Found information on " + name + " in cache, " +
                    "\nclick <Load from cache> to get information from cache," +
                    "\nclick <Search character> to get information from API");
            lastSearched = name;

        } else {
            //ask model to get info from API
            model.getCharacterInfo(name);
            lastSearched = "";
        }
    }

    /**
     * Notified when the Comic button above the table is clicked.
     *
     * <p>Retrieve current character in model</p>
     *
     * <p>Calls view's updateCenterComics() to populate the table.</p>
     */
    @Override
    public void onComics(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewComics(model.getCurrentCharacter().getComicList());
        }

    }

    /**
     * Notified when the Url button above the table is clicked.
     *
     * <p>Retrieve current character in model</p>
     *
     * <p>Ask view to display list of URLs for public websites tha has character information</p>
     */
    @Override
    public void onUrls(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewResourceUrls(model.getCurrentCharacter().getUrls());
        }
    }

    /**
     * Notified when the Stories button above the table is clicked.
     *
     * <p>Retrieve current character in model</p>
     *
     * <p>Ask view to display list of Stories that the character appears</p>
     */
    @Override
    public void onStories(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewStories(model.getCurrentCharacter().getStoryList());
        }
    }
    /**
     * Notified when the Events button above the table is clicked.
     *
     * <p>Retrieve current character in model</p>
     *
     * <p>Ask view to display list of events that the character appears</p>
     */
    @Override
    public void onEvents(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewEvents(model.getCurrentCharacter().getEventList());
        }
    }
    /**
     * Notified the Series button above the table is clicked.
     *
     * <p>Retrieve current character in model</p>
     *
     * <p>Ask view to display list of series that the character appears</p>
     */
    @Override
    public void onSeries(){
        if(model.getCurrentCharacter() != null){
            view.updateTableViewSeries(model.getCurrentCharacter().getSeriesList());
        }
    }

    /**
     * Notified when the Send Report button is clicked.
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
     * Notified when the load from cache button is clicked
     *
     * <p>Ask model to load data corresponding to given name from cache</p>
     */
    @Override
    public void onLoadFromCache(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }

        model.loadInfoFromCache(name);
        lastSearched = "";
    }

    /**
     * Retrieves updated CharacterInfo object from model
     *
     * <p>Ask view to display character summary on lower left text area,
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
     * Retrieves updated URL to report paste from model.
     *
     * <p>Ask view to display paste report URL in lower left text area</p>
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
