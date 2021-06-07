package marvel;

import marvel.model.ConfigHandler;
import marvel.model.ModelFacade;
import marvel.model.ModelImpl;
import marvel.model.ModelObserver;
import marvel.model.input.InputModel;
import marvel.model.input.OfflineMarvelModel;
import marvel.model.output.OutputModel;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class UnitTests {
    /**
     * Reference to the model used in tests
     *
     * <p>May be mocked</p>
     */
    ModelFacade model;
    /**
     * Reference to the input submodel used in tests
     *
     * <p>May be mocked</p>
     */
    InputModel input;
    /**
     * Reference to the input submodel used in tests
     *
     * <p>May be mocked</p>
     */
    OutputModel output;

    /**
     * Path to keys configuration file for initialising model
     */
    String configFilePath = "./src/main/resources/marvel/KeyConfig.json";


    /**
     * Unit testing concrete class ConfigHandler
     *
     * <p>ConfigHandler used for getting API Keys configuration for sending input and output API requests</p>
     */
    @Test
    public void testConfigHandler(){
        ConfigHandler handler = new ConfigHandler("./src/main/resources/marvel/KeyConfig.json");
        assertNotEquals("", handler.getInputPublicKey());
        assertNotEquals("", handler.getInputPrivateKey());
        assertNotEquals("", handler.getOutputKey());
    }

    /**
     * Testing image path retrieval from provided CharacterInfo object using concrete offline model.
     *
     * <p>Test using concrete OfflineMarvelModel,
     * testing behaviour between ModelFacade and InputModel
     * when model's getImagePathByInfo() is called</p>
     */
    @Test
    public void testInputModelGetThumbnailFullPathOffline(){
        //uses concrete input model
        InputModel input = new OfflineMarvelModel();
        OutputModel output = mock(OutputModel.class);
        //GIVEN
        model = new ModelImpl(input, output, configFilePath);
        model.getCharacterInfo("spider-man");
        //WHEN
        String path = model.getImagePathByInfo(model.getCurrentCharacter());
        //THEN
        assertNull(path);
    }
}
