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
     * Reference to a CharacterInfo that was built from the last successful search
     */
    CharacterInfo currentCharacter;
    /**
     * List of ModelObservers that listens to this model for requests completion.
     */
    List<ModelObserver> observers;

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

        input.setApiHandler(new MarvelApiHandler(handler.getInputPublicKey(), handler.getInputPrivateKey()));
        output.setApiHandler(new PastebinApiHandler(handler.getOutputKey()));
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
     * Ask input sub model to conduct search for character information with provided name.
     *
     * <p>Calls notifyObserverGetInfoComplete() to notify all observers when operation is done.</p>
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
        output.sendReport(info);
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


}
