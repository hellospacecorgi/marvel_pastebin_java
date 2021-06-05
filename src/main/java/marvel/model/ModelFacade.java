package marvel.model;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;

public interface ModelFacade {
    public InputModel getInputSubModel();
    public OutputModel getOutputSubModel();

    public CharacterInfo getCurrentCharacter();

    public CharacterInfo getCharacterInfo(String name);

    public Image getImageByInfo(CharacterInfo info);

    //public boolean sendReport();
}
