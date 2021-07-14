package marvel.model;

import marvel.model.character.CharacterInfo;
import marvel.model.input.CacheHandler;
import marvel.model.input.InputModel;
import marvel.model.input.MarvelApiHandler;
import marvel.model.input.ResponseHandler;
import marvel.model.output.OutputModel;
import marvel.model.output.PastebinApiHandler;
import marvel.model.output.ReportService;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for clients to make mutable and accessor calls to APIs.
 * Acts as an interface for the client to interact with the complex model subsystem.
 *
 * @see ModelFacade
 * @see InputModel
 * @see OutputModel
 */
public class ModelImpl implements ModelFacade{
    /**
     * Reference to a InputModel
     */
    InputModel input;
    /**
     * Reference to a OutputModel
     */
    OutputModel output;
    /**
     * Reference to a CharacterInfo that was built from the last successful search
     */
    CharacterInfo currentCharacter;
    /**
     * List of ModelObservers that listens to this model for requests completion.
     */
    List<ModelObserver> observers;

    /**
     * Reference integer selected by user
     *
     * <p>Used as matching index in search list for swapping out names when list is full</p>
     */
    private int indexSelected;

    /**
     * Contains up to 3 names of characters searched by user
     */
    private List<String> searchedList;

    /**
     * Number of searches performed by user
     */
    private int searchCount = 0;

    /**
     * Takes in a version of InputModel and OutputModel for online/offline versions.
     * @param input A InputModel object that can be online or offline
     * @param output A OutputModel object that can be online or offline
     * @param handler A ConfigHandler object used for getting API keys
     */
    public ModelImpl(InputModel input, OutputModel output, ConfigHandler handler){
        this.input = input;
        this.output = output;
        this.observers = new ArrayList<>();
        this.searchedList = new ArrayList<>();

        input.setApiHandler(new MarvelApiHandler(handler.getInputPublicKey(), handler.getInputPrivateKey()));
        output.setApiHandler(new PastebinApiHandler(handler.getOutputKey()));

        input.setResponseHandler(new ResponseHandler());
        input.setCacheHandler(new CacheHandler());
        output.setReportService(new ReportService());

    }

    /**
     * Getter for input sub model
     * @return InputModel - InputModel object that handles functionalities to input API
     */
    @Override
    public InputModel getInputSubModel() {
        return input;
    }

    /**
     * Getter for output sub model
     * @return OutputModel - OutputModel object that handles functionalities to output API
     */
    @Override
    public OutputModel getOutputSubModel() {
        return output;
    }

    /**
     * Performs operations needed to retrieve information on character matching given name
     *
     * <p>Ask input sub model to conduct search for character information with provided name.</p>
     *
     * <p>Upon successful search, update searched list with names of character,
     * adding name to list in order of search when list is not full and
     * replacing name in list at matching index chosen by user when list is full</p>
     *
     * <p>After input model completed request, notify observers request complete</p>
     *
     * <p>After searched list is updated, notify observers about the update</p>
     *
     * @param name String of name of character to search API with.
     */
    @Override
    public void getCharacterInfo(String name) {
        if(name == null){
            throw new NullPointerException();
        }
        if(name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException();
        }
        currentCharacter = input.getInfoByName(name);
        notifyObserversGetInfoComplete();

        //Add name to search list and notify observers
        if (currentCharacter != null) {
            if(searchedList.size() < 3){
                searchedList.add(name);
            } else {
                searchedList.set(indexSelected, name);
            }
            searchCount++;
            notifyObserversSearchedListUpdated();
        }
    }

    /**
     * Retrieves current CharacterInfo in model
     *
     * @return CharacterInfo - CharacterInfo object built from last successful search
     */
    @Override
    public CharacterInfo getCurrentCharacter() {
        return currentCharacter;
    }

    /**
     * Retrieves full URL path to thumbnail given a CharacterInfo object
     *
     * @param info CharacterInfo object that contains data
     * @return String - Full URL path for thumbnail image if info is not null, otherwise return null
     */
    @Override
    public String getImagePathByInfo(CharacterInfo info){
        if(info == null){
            return null;
        }
        return input.getThumbnailFullPath(info);
    }

    /**
     * Ask output sub model to send a report based on provided CharacterInfo's data.
     *
     * <p>Calls notifyObserverGetInfoComplete() to notify all observers when operation is done.</p>
     *
     * @param info - CharacterInfo object that contains data to generate report with
     */
    @Override
    public void sendReport(CharacterInfo info) {
        if(info == null){
            return;
        }

        List<String> unmatchedNames = new ArrayList<>();
        if(searchCount < 4){
            indexSelected = searchedList.size() - 1;
        }
        for(int i = 0 ; i < searchedList.size() ; i ++){
            if(i != indexSelected){
                unmatchedNames.add(searchedList.get(i));
            }
        }
        output.sendReport(info, unmatchedNames);
        notifyObserversSendReportComplete();
    }
    /**
     * Retrieve pastebin URL for paste for the last report sent.
     *
     * @return String - URL to paste generated for last report sent
     */
    @Override
    public String getReportUrl() {
        return output.getReportUrl();
    }

    /**
     * Ask input sub model to search database if record with key matching given name
     *
     * @param name String to search database for matching record
     * @return boolean - return true if record found, otherwise false
     */
    @Override
    public boolean isInfoInCache(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        return input.isInfoInCache(name);
    }

    /**
     * Performs operations needed to retrieve information on character matching given name from the cache database
     *
     * <p>Ask input model to search for record with name as key from cache</p>
     *
     * <p>If found and CharacterInfo created from record, set current character reference to it</p>
     *
     * <p>Upon successful retrieval, update searched list with name of character,
     * adding name to list in order of search when list is not full and
     * replacing name in list at matching index chosen by user when list is full</p>
     *
     * <p>After input model completed request, notify observers request complete</p>
     *
     * <p>After searched list is updated, notify observers about the update</p>
     *
     * @param name String to search database for matching record
     */
    @Override
    public void loadInfoFromCache(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        if(input.isInfoInCache(name)){
            CharacterInfo info = input.getInfoByNameFromCache(name);
            if(info != null){
                currentCharacter = info;
            }
            notifyObserversGetInfoComplete();

            //Add name to search list and notify observers
            if (currentCharacter != null) {
                if(searchedList.size() < 3){
                    searchedList.add(name);
                } else {
                    searchedList.set(indexSelected, name);
                }
                searchCount++;
                notifyObserversSearchedListUpdated();
            }
        }
    }

    /**
     * Adds an observer to list of ModelObserver, to be notified upon API requests completed
     *
     * @param obs - ModelObserver object to be added
     */
    @Override
    public void addObserver(ModelObserver obs) {
        observers.add(obs);
    }

    /**
     * Notify all observers to update character info
     */
    @Override
    public void notifyObserversGetInfoComplete() {
        for(int i = 0 ; i < observers.size() ; i++){
            observers.get(i).updateCharacterInfo();
        }
    }

    /**
     * Notify all observers to update report URL
     */
    @Override
    public void notifyObserversSendReportComplete(){
        for(int i = 0 ; i < observers.size() ; i++){
            observers.get(i).updateReportUrl();
        }
    }

    /**
     * Notify all observers to update searched list
     */
    public void notifyObserversSearchedListUpdated(){
        for(int i = 0 ; i < observers.size() ; i++){
            observers.get(i).updateSearchedList();
        }
    }

    /**
     * Sets the integer selected as index in list of searched characters
     *
     * @param index integer selected by user to be used for swapping out names in searched list, must be in range 0-2
     */
    public void setIndexSelected(int index){
        if(index < 0 || index > 2){
            throw new IllegalArgumentException();
        }
        this.indexSelected = index;
    }
    /**
     * Retrieve searched list of names of characters searched
     *
     * @return List<String> - list of names of characters searched
     */
    public List<String> getSearchedList(){
        return this.searchedList;
    }


}
