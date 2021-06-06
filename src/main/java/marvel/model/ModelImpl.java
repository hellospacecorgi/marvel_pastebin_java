package marvel.model;

import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;
import marvel.model.input.MarvelApiHandler;
import marvel.model.output.OutputModel;
import marvel.model.output.PastebinApiHandler;

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
     * Reference to a CharacterInfo that was built from last successful character GET request
     */
    CharacterInfo currentCharacter;
    /**
     * List of ModelObservers that listens to this model for requests completion.
     */
    List<ModelObserver> observers;

    /**
     * Takes in a version of InputModel and OutputModel for online/offline versions.
     * @param input takes in a input model implementation
     * @param output takes in a output model implementation
     */
    public ModelImpl(InputModel input, OutputModel output, String configFilePath){
        this.input = input;
        this.output = output;
        this.observers = new ArrayList<>();

        ConfigHandler config = new ConfigHandler(configFilePath);
        this.input.setApiHandler(new MarvelApiHandler(config.getInputPublicKey(), config.getInputPrivateKey()));
        this.output.setApiHandler(new PastebinApiHandler(config.getOutputKey()));
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
     * Uses injected instance of InputModel to conduct search for character information with `name` name.
     *
     * Calls notifyObserverGetInfoComplete() to notify all observers.
     *
     * @param name String of name of character to search API with.
     * @return CharacterInfo object that stores information and links to resources related to character of `name` name.
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
    }

    /**
     * Retrieves current CharacterInfo in model
     *
     * @return CharacterInfo - CharacterInfo object built from last successful GET request
     */
    @Override
    public CharacterInfo getCurrentCharacter() {
        return currentCharacter;
    }

    /**
     * Retrieves full URL path to thumbnail given CharacterInfo object
     *
     * @param info - CharacterInfo object that contains data
     * @return Stirng - Full URL path for thumbnail image
     */
    @Override
    public String getImagePathByInfo(CharacterInfo info){
        if(info == null){
            return null;
        }
        return input.getThumbnailFullPath(info);
    }

    /**
     * Generates and sends report based on provided CharacterInfo's data
     *
     * @param info - CharacterInfo object that contains data to generate report with
     */
    @Override
    public void sendReport(CharacterInfo info) {
        output.sendReport(info);
        notifyObserversSendReportComplete();
    }
    /**
     * Retrieve pastebin URL for paste that contains last report sent
     *
     * @return String - URL to paste generated for last report sent
     */
    @Override
    public String getReportUrl() {
        return output.getReportUrl();
    }
    /**
     * Adds observer to list of ModelObserver, to be notified upon API requests completed
     *
     * @param obs - ModelObserver object
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


}
