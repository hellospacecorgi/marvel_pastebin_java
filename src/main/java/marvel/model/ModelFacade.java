package marvel.model;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;
import marvel.model.output.OutputModel;

public interface ModelFacade {
    public InputModel getInputSubModel();
    public OutputModel getOutputSubModel();

    public CharacterInfo getCurrentCharacter();

    public CharacterInfo getCharacterInfo(String name);

    public String getImagePathByInfo(CharacterInfo info);

    public boolean sendReport(CharacterInfo info);
    public String getReportUrl();

    public void addObserver(ModelObserver obs);
    public void notifyObserversGetInfoComplete();
    public void notifyObserversSendReportComplete();
}
