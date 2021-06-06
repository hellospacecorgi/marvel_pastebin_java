package marvel.model;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;
import marvel.model.output.OutputModel;

/**
 * ModelFacade interface for interacting with model operations.
 *
 * @see InputModel
 * @see OutputModel
 * @see ModelImpl
 */
public interface ModelFacade {
    /**
     * Getter for input sub model
     * @return InputModel - InputModel object that handles functionalities to input API
     */
    public InputModel getInputSubModel();

    /**
     * Getter for output sub model
     * @return OutputModel - OutputModel object that handles functionalities to output API
     */
    public OutputModel getOutputSubModel();

    //Mutable calls
    /**
     * Conduct search for character information using String name provided
     *
     * @param name - name of character to get information with
     */
    public void getCharacterInfo(String name);

    /**
     * Generates and sends report based on provided CharacterInfo's data
     *
     * @param info - CharacterInfo object that contains data to generate report with
     */
    public void sendReport(CharacterInfo info);

    //Accessor calls
    /**
     * Getter for input model's current CharacterInfo that it retrieved from last successful search
     *
     * @return CharacterInfo - CharacterInfo object that input model kept reference to
     */
    public CharacterInfo getCurrentCharacter();

    /**
     * Retrieve full URL path for thumbnail image given a CharacterInfo object
     *
     * @param info - CharacterInfo object that contains data
     * @return String - Full URL path to retrieve thumbnail image
     */
    public String getImagePathByInfo(CharacterInfo info);
    /**
     * Retrieve pastebin URL for paste that contains last report sent
     *
     * @return String - URL to paste generated for last report sent
     */
    public String getReportUrl();

    /**
     * Adds observer to list of ModelObserver, to be notified upon API requests completed
     *
     * @param obs - ModelObserver object
     */
    public void addObserver(ModelObserver obs);

    /**
     * Notify all observers to update character info
     */
    public void notifyObserversGetInfoComplete();
    /**
     * Notify all observers to update report URL
     */
    public void notifyObserversSendReportComplete();
}
