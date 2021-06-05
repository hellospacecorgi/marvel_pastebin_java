package marvel.model;

import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;
import marvel.model.input.MarvelApiHandler;

/**
 * Provides methods for clients to make mutable and accessor calls to APIs.
 * Acts as an interface for the client to interact with the complex model subsystem.
 *
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
        this.input = input;
        this.output = output;

        ConfigHandler config = new ConfigHandler(configFilePath);
        this.input.setApiHandler(new MarvelApiHandler());

    }

    @Override
    public InputModel getInputSubModel() {
        return input;
    }

    @Override
    public OutputModel getOutputSubModel() {
        return output;
    }

    /**
     * Uses injected instance of InputModel to conduct search for character information with `name` name.
     *
     * @param name String of name of character to search API with.
     * @return CharacterInfo object that stores information and links to resources related to character of `name` name.
     */
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
