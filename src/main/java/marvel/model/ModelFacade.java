package marvel.model;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;
import marvel.model.output.OutputModel;

/**
 *
 */
public interface ModelFacade {
    public InputModel getInputSubModel();
    public OutputModel getOutputSubModel();

    //Mutable calls
    /**
     *
     * @param name
     */
    public void getCharacterInfo(String name);

    /**
     *
     * @param info
     */
    public void sendReport(CharacterInfo info);

    //Accessor calls
    /**
     *
     * @return
     */
    public CharacterInfo getCurrentCharacter();
    public String getImagePathByInfo(CharacterInfo info);
    public String getReportUrl();

    /**
     *
     * @param obs
     */
    public void addObserver(ModelObserver obs);
    public void notifyObserversGetInfoComplete();
    public void notifyObserversSendReportComplete();
}
