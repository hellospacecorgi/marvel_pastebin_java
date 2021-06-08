package marvel;

import marvel.model.ConfigHandler;
import marvel.model.ModelFacade;
import marvel.model.ModelImpl;
import marvel.model.ModelObserver;
import marvel.model.character.CharacterInfo;
import marvel.model.character.ResourceUrl;
import marvel.model.character.Thumbnail;
import marvel.model.input.InputModel;
import marvel.model.output.OnlinePastebinModel;
import marvel.model.output.OutputModel;
import marvel.model.output.PastebinApiHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class OnlinePastebinModelTest {
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
     * Reference to a observer used in tests
     *
     * <p>May be mocked</p>
     */
    ModelObserver observer;
    /**
     * Path to keys configuration file for initialising model
     */
    String configFilePath = "./src/main/resources/marvel/KeyConfig.json";

    /**
     * Path to a dummy image for character thumbnail accessor methods
     */
    String imgPath = "./src/main/resources/marvel/dummy.png";
    /**
     * Reference to a character object used in tests
     */
    CharacterInfo spiderman;

    /**
     * Tagged by @Before, called before every test.
     *
     *  <p>Used to specify GIVEN behaviour for mocked input model class on valid and invalid search names</p>
     *
     * @see Before
     */
    @Before
    public void ModelImplSetUp(){
        //mock API classes
        input = mock(InputModel.class);
        output = mock(OutputModel.class);

        //GIVEN ModelImpl initialised with an instance of InputModel and OutputModel
        ConfigHandler handler = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, handler);

        //GIVEN - CharacterInfo build from valid name search response
        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));
        Thumbnail thb = new Thumbnail("./src/main/resources/marvel/dummy.png", "png");
        spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setUrls(urls);
        spiderman.setThumbnail(new Thumbnail("fakepath", "jpg"));

        //GIVEN searching for valid character name search triggered will return valid CharacterInfo built from data
        when(input.getInfoByName("spider-man")).thenReturn(spiderman);

        //GIVEN searching for invalid character name - Not a marvel character will return null
        when(input.getInfoByName("wonder-woman")).thenReturn(null);

    }

    /**
     * Test that PastebinApiHandler's sendReport() is called when ModelFacade's sendReport() is called
     *
     * <p>Testing sendReport() one layer below ModelFacade, using instance of OnlinePastebinModel.</p>
     *
     * <p>Testing with mock PastebinApiHandler, testing interaction between OnlinePastebinModel and PastebinHandler.
     *
     * <p>PastebinApiHandler is mocked using Mockito</p>
     */
    @Test
    public void testOutputModelSendReport(){

        //GIVEN
        output = new OnlinePastebinModel();
        input = mock(InputModel.class);
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getOutputSubModel().setApiHandler(handler);

        //WHEN
        model.sendReport(spiderman);
        //THEN
        verify(handler, times(1)).sendReport(anyString(), anyString());

    }

    /**
     * Testing for handler trigger in getReportUrl() when null info passed
     */
    @Test
    public void testGetReportUrlNullInfo(){
        //GIVEN
        OutputModel output = new OnlinePastebinModel();
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getOutputSubModel().setApiHandler(handler);

        //WHEN
        model.sendReport(null);

        //THEN
        verify(handler, times(0)).sendReport(anyString(), anyString());
    }

    /**
     * Testing for handler trigger in getReportUrl() when no previous report sent
     */
    @Test
    public void testGetReportUrlWithoutSendingFirst(){
        //GIVEN
        OutputModel output = new OnlinePastebinModel();
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getOutputSubModel().setApiHandler(handler);

        //WHEN
        String url = model.getReportUrl();

        //THEN
        verify(handler, times(1)).getOutputUrl();
        assertNull(url);
    }

}
