package marvel;

import marvel.model.*;
import marvel.model.character.CharacterInfo;
import marvel.model.character.ResourceUrl;
import marvel.model.character.Thumbnail;
import marvel.model.input.InputModel;
import marvel.model.input.MarvelApiHandler;
import marvel.model.input.OfflineMarvelModel;
import marvel.model.input.OnlineMarvelModel;
import marvel.model.output.OfflinePastebinModel;
import marvel.model.output.OnlinePastebinModel;
import marvel.model.output.OutputModel;
import marvel.model.output.PastebinApiHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class used as test suite for testing ModelImpl, an implementation of the ModelFacade interface.
 *
 * <p>Tests written following the Test-Driven-Development process, where tests go through a RED-GREEN-REFACTOR process as features are added and implemented.</p>
 *
 * <p>Tests might undergo refactoring after the initial GREEN-REFACTOR as methods are removed or restructured, these changes are on a method name / return value level, and logic within a test case remain largely the same</p>
 */
public class ModelImplTest {
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
        model = new ModelImpl(input, output, configFilePath);

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
     * Testing ModelImpl sub model retrieval method
     */
    @Test
    public void testGetSubModels(){
        assertNotNull(model.getInputSubModel());
        assertNotNull(model.getOutputSubModel());

        assertEquals(model.getInputSubModel(), input);
        assertEquals(model.getOutputSubModel(), output);
    }

    /**
     * Test for expected coordination behavior from ModelImpl to InputModel instance when searching for a valid name.
     *
     * <p>See ModelImplSetUp() for mocked input model behavior.</p>
     */
    @Test
    public void testValidCharacterName(){

        //WHEN - searching valid character name
        model.getCharacterInfo("spider-man");

        //THEN - expect input model's getInfoByName will be triggered
        verify(input, times(1)).getInfoByName("spider-man");

        //THEN - expect returned CharacterInfo object matches expected valid object

        /*
         * Refactored test from info to model.getCurrentCharacter
         */
        assertEquals(1234, model.getCurrentCharacter().getId());
        assertEquals("spiderman", model.getCurrentCharacter().getName());
        assertEquals("fakepath", model.getCurrentCharacter().getThumbnail().getPath());
        List<ResourceUrl> urls = model.getCurrentCharacter().getUrls();
        assertEquals(2, urls.size());
        assertEquals("dummy-url.com", urls.get(0).getUrl());
        assertEquals("wiki", urls.get(0).getType());
        assertEquals("another-dummy.com", urls.get(1).getUrl());
        assertEquals("blog", urls.get(1).getType());

    }

    /**
     * Test for expected coordination behavior from ModelImpl to InputModel instance when searching for invalid name.
     *
     * <p>See ModelImplSetUp() for mocked input model behavior.</p>
     */
    @Test
    public void testInvalidCharacterName(){

        //WHEN - searching for invalid character name
        model.getCharacterInfo("wonder-woman");

        //THEN - expect InputModel method to be triggered
        verify(input, times(1)).getInfoByName("wonder-woman");

//        //THEN - expect null return returned
//        assertNull(info);
    }

    /**
     * Testing InputModel's getInfoByName() invalid name search
     *
     * <p>Testing a one layer down ModelFacade - specifically for OnlineMarvelModel's interaction with
     * the concrete class MarvelApiHandler which is responsible for sending requests that hits the web API.</p>
     *
     * <p>See ModelImplSetUp() for mocked input model behavior.</p>
     */
    @Test
    public void testInputModelGetInfoByNameValid(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);

        //GIVEN
        model = new ModelImpl(input, output, configFilePath);
        model.getInputSubModel().setApiHandler(handler);

        //GIVEN
        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));
        Thumbnail thb = new Thumbnail("./src/main/resources/marvel/dummy.png", "png");
        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setUrls(urls);
        spiderman.setThumbnail(new Thumbnail("fakepath", "jpg"));

        //GIVEN
        when(handler.getCharacterInfoByName("spider-man")).thenReturn(spiderman);

        //WHEN
        model.getCharacterInfo("spider-man");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("spider-man");

        /*
         * Refactored to use model.getCurrentCharacter() after refactoring return value of getCharacterInfo to void
         * to prepare for observer pattern refactoring
         */
        assertEquals(spiderman.getName(), model.getCurrentCharacter().getName());
        assertEquals(spiderman.getDescription(), model.getCurrentCharacter().getDescription());
        assertEquals(spiderman.getModified(), model.getCurrentCharacter().getModified());

    }

    /**
     * Testing InputModel's getInfoByName() invalid name search
     *
     * <p>Testing a one layer below ModelFacade - specifically for OnlineMarvelModel's interaction with
     * the concrete class MarvelApiHandler which is responsible for sending requests that hits the web API.</p>
     *
     * <p>MarvelApiHandler is mocked using Mockito.</p>
     *
     */
    @Test
    public void testInputModelGetInfoByNameInvalid(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);

        //GIVEN
        model = new ModelImpl(input, output, configFilePath);
        model.getInputSubModel().setApiHandler(handler);

        //GIVEN
        when(handler.getCharacterInfoByName("invalid")).thenReturn(null);

        //WHEN
        model.getCharacterInfo("invalid");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("invalid");

    }

    /**
     * Testing expected CharacterInfo returned from input model after search.
     *
     * <p>MarvelApiHandler is mocked using Mockito.</p>
     */
    @Test
    public void testInputModelGetInfoNullList(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);

        //GIVEN
        model = new ModelImpl(input, output, configFilePath);
        model.getInputSubModel().setApiHandler(handler);

        //GIVEN
        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setThumbnail(null);
        spiderman.setStoryList(null);

        //GIVEN
        when(handler.getCharacterInfoByName("spider-man")).thenReturn(spiderman);

        //WHEN
        model.getCharacterInfo("spider-man");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("spider-man");
        /*
         * Refactored test to use model.getCurrentCharacter()
         *  after refactoring return value of getCharacterInfo to void
         * to prepare for observer pattern refactoring
         */
        assertEquals(spiderman.getName(), model.getCurrentCharacter().getName());
        assertEquals(spiderman.getDescription(), model.getCurrentCharacter().getDescription());
        assertEquals(spiderman.getModified(), model.getCurrentCharacter().getModified());
        assertNull(model.getCurrentCharacter().getThumbnail());
        assertNull(model.getCurrentCharacter().getStoryList());
    }

    /**
     * Testing image path retrieval from provided CharacterInfo object.
     *
     * <p>Test using mocked InputModel,
     * testing behaviour between ModelFacade and InputModel
     * when model's getImagePathByInfo() is called</p>
     *
     * <p>See ModelImplSetUp() for mocked input model behavior.</p>
     */
    @Test
    public void testGetImagePathViaModelFacade(){
        //uses mock InputModel
        //GIVEN
        when(input.getThumbnailFullPath(spiderman)).thenReturn("./src/main/resources/marvel/dummy.png");

        //WHEN
        String path = model.getImagePathByInfo(spiderman);

        //THEN
        verify(input, times(1)).getThumbnailFullPath(spiderman);
        assertEquals("./src/main/resources/marvel/dummy.png", path);

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
        //uses concrete model
        input = new OfflineMarvelModel();

        //GIVEN
        model = new ModelImpl(input, output, configFilePath);
        model.getCharacterInfo("spider-man");
        //WHEN
        String path = model.getImagePathByInfo(model.getCurrentCharacter());
        //THEN
        assertNull(path);
    }

    /**
     * Test getImageByInfo() behaviour when CharacterInfo object has null thumbnail attribute.
     */
    @Test
    public void testNullThumbnailCharacter(){
        //GIVEN
        CharacterInfo dummy = new CharacterInfo(2222, "dummy","Some dummy hero", "199-23-23");
        dummy.setThumbnail(null);
        //WHEN-THEN
        assertNull(model.getImagePathByInfo(dummy));

        //GIVEN
        dummy.setThumbnail(new Thumbnail("", ""));
        //WHEN-THEN
        assertNull(model.getImagePathByInfo(dummy));

    }

    /**
     * Testing ModelFacade sendReport() behavior for valid and invalid CharacterInfo passed in.
     *
     * <p>Test for interaction between ModelImpl and OutputModel</p>
     */
    @Test
    public void testSendReportFacade(){
        //WHEN
        model.sendReport(spiderman);
        //THEN
        verify(output, times(1)).sendReport(spiderman);

        //WHEN
        model.sendReport(null);
        //THEN
        verify(output, times(1)).sendReport(spiderman);

    }

    /**
     * Testing output model's getReportUrl() triggered when model facade's getReportUrl() is called.
     */
    @Test
    public void testGetReportUrlFacade(){
        //GIVEN
        when(output.getReportUrl()).thenReturn("dummy.url");
        //WHEN
        String ret = model.getReportUrl();
        //THEN
        verify(output, times(1)).getReportUrl();
        assertEquals(ret, "dummy.url");
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
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        model = new ModelImpl(input, output, configFilePath);
        model.getOutputSubModel().setApiHandler(handler);
        //WHEN
        model.sendReport(spiderman);
        //THEN
        verify(handler, times(1)).sendReport(anyString(), anyString());

    }

    /**
     * Test that PastebinApiHandler's sendReport() is called when ModelFacade's sendReport() is called
     *
     * <p>Testing ModelFacade's getReportUrl() interaction with OutputModel</p>
     *
     * <p>Testing getReportUrl() one layer below ModelFacade, using instance of OnlinePastebinModel.</p>
     *
     * <p>Testing with mock PastebinApiHandler, testing interaction between OnlinePastebinModel and PastebinHandler.</p>
     */
    @Test
    public void testOutputModelGetReportUrl(){
        //GIVEN
        when(output.getReportUrl()).thenReturn("dummy-report-url");
        //WHEN
        String ret = model.getReportUrl();
        //THEN
        verify(output, times(1)).getReportUrl();
        assertEquals("dummy-report-url", ret);

        //GIVEN
        output = new OfflinePastebinModel();
        model = new ModelImpl(input, output, configFilePath);
        //WHEN-THEN
        assertEquals("dummy-report-output-url", model.getReportUrl());

    }

    /**
     * Testing updateCharacterInfo() in ModelObserver called by ModelFacade notify methods
     *
     * <p>Test for Observer pattern refactored to ModelImpl with added interface ModelObserver</p>
     *
     * <p>Refactoring added addObserver(), notifyObserverGet/SendComplete() to ModelFacade</p>
     *
     * <p>Added new interface ModelObserver</p>
     */
    @Test
    public void testRefactoredObserverUpdateGetCharacterInfoComplete(){
        //GIVEN
        observer = mock(ModelObserver.class);
        model.addObserver(observer);
        ModelObserver obs2 = mock(ModelObserver.class);
        model.addObserver(obs2);

        //WHEN
        model.getCharacterInfo("spider-man");

        //THEN
        verify(observer, times(1)).updateCharacterInfo();
        verify(obs2, times(1)).updateCharacterInfo();
    }

    /**
     * Testing updateReportUrl() in ModelObserver called by ModelFacade's notify methods
     *
     * <p>Test for Observer pattern refactored to ModelImpl with added interface ModelObserver</p>
     *
     * <p>Refactoring added addObserver(), notifyObserverGet/SendComplete() to ModelFacade</p>
     *
     * <p>Added new interface ModelObserver</p>
     */
    @Test
    public void testRefactoredObserverUpdateSendReportComplete(){
        //GIVEN
        observer = mock(ModelObserver.class);
        model.addObserver(observer);
        ModelObserver obs2 = mock(ModelObserver.class);
        model.addObserver(obs2);

        //WHEN
        model.getCharacterInfo("spider-man");
        //THEN
        verify(observer, times(1)).updateCharacterInfo();
        verify(obs2, times(1)).updateCharacterInfo();
        //WHEN
        model.sendReport(model.getCurrentCharacter());
        //THEN
        verify(observer, times(1)).updateReportUrl();
        verify(obs2, times(1)).updateReportUrl();

    }

}
