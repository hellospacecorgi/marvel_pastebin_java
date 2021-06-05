package marvel.model;

import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;

public interface ModelFacade {
    public InputModel getInputSubModel();
    public OutputModel getOutputSubModel();

    public CharacterInfo getCharacterInfo(String name);

    //public boolean sendReport();
}
