package marvel.model;

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

        return null;
    }
}
