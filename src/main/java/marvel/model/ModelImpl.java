package marvel.model;

import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;

/**
 * @see ModelFacade
 */
public class ModelImpl implements ModelFacade{
    InputModel input;
    OutputModel output;

    /**
     * Takes in a version of InputModel and OutputModel for online/offline versions.
     * @param input takes in a input model implementation
     * @param output takes in a output model implementation
     */
    public ModelImpl(InputModel input, OutputModel output, String configFilePath){
        ConfigHandler config = new ConfigHandler(configFilePath);
        input.setApiHandlerKey(config.getInputKey());
        this.input = input;
        this.output = output;
    }

    @Override
    public InputModel getInputSubModel() {
        return input;
    }

    @Override
    public OutputModel getOutputSubModel() {
        return output;
    }

    @Override
    public CharacterInfo getCharacterInfo(String name) {
        if(name == null){
            throw new NullPointerException();
        }
        if(name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException();
        }
        return input.getInfoByName(name);
    }
}
