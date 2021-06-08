package marvel;

import marvel.model.ConfigHandler;
import marvel.model.ModelFacade;
import marvel.model.ModelImpl;
import marvel.model.ModelObserver;
import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;
import marvel.model.input.OfflineMarvelModel;
import marvel.model.output.OfflinePastebinModel;
import marvel.model.output.OutputModel;
import org.junit.Test;

import static org.junit.Assert.*;
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
        input = new OfflineMarvelModel();
        output = mock(OutputModel.class);
        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getCharacterInfo("spider-man");
        //WHEN
        String path = model.getImagePathByInfo(model.getCurrentCharacter());
        //THEN
        assertNull(path);
    }

    @Test
    public void testOfflinePastebinGetReportUrl(){
        //GIVEN
        output = new OfflinePastebinModel();
        input = mock(InputModel.class);
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        //WHEN-THEN
        assertEquals("dummy-report-output-url", model.getReportUrl());
    }

    @Test
    public void testOfflineMarvelModelGetCharacter(){
        //GIVEN
        output = mock(OutputModel.class);
        input = new OfflineMarvelModel();
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);

        //WHEN
        CharacterInfo info = input.getInfoByName("dummy");

        //THEN
        assertEquals(info.getId(), 123456);
        assertEquals(info.getName(), "Dummy Hero");
        assertEquals(info.getNComics(), 1);
        assertEquals(info.getNSeries(), 3);
        assertEquals(info.getNStories(), 2);
        assertEquals(info.getNEvents(), 4);
        assertEquals(info.getUrls().size(), 3);
    }
}
