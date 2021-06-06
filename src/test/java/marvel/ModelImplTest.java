package marvel;

import javafx.beans.binding.When;
import javafx.scene.image.Image;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ModelImplTest {
    ModelFacade model;
    InputModel input;
    OutputModel output;
    String configFilePath = "./src/main/resources/marvel/KeyConfig.json";
    String imgPath = "./src/main/resources/marvel/dummy.png";
    CharacterInfo spiderman;

    /**
     *  Used to specify GIVEN behaviour for mocked input model class on valid and invalid search names
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
     * Used for getting API Keys configuration for sending input and output API requests
     */
    @Test
    public void testConfigHandler(){
        ConfigHandler handler = new ConfigHandler("./src/main/resources/marvel/KeyConfig.json");
        assertNotEquals("", handler.getInputPublicKey());
        assertNotEquals("", handler.getInputPrivateKey());
        assertNotEquals("", handler.getOutputKey());
    }

    /**
     * Testing ModelImpl submodel retrieval method
     */
    @Test
    public void testGetSubModels(){
        assertNotNull(model.getInputSubModel());
        assertNotNull(model.getOutputSubModel());

        assertEquals(model.getInputSubModel(), input);
        assertEquals(model.getOutputSubModel(), output);
    }

    /**
     * Test for expected coordination behavior from ModelImpl to InputModel instance when searching for valid name.
     * See GIVEN @Before setup method.
     */
    @Test
    public void testValidCharacterName(){

        //WHEN - searching valid character name
        CharacterInfo info = model.getCharacterInfo("spider-man");

        //THEN - expect input model's getInfoByName will be triggered
        verify(input, times(1)).getInfoByName("spider-man");

        //THEN - expect returned CharacterInfo object matches expected valid object
        assertEquals(1234, info.getId());
        assertEquals("spiderman", info.getName());
        assertEquals("fakepath", info.getThumbnail().getPath());
        List<ResourceUrl> urls = info.getUrls();
        assertEquals(2, urls.size());
        assertEquals("dummy-url.com", urls.get(0).getUrl());
        assertEquals("wiki", urls.get(0).getType());
        assertEquals("another-dummy.com", urls.get(1).getUrl());
        assertEquals("blog", urls.get(1).getType());

    }

    /**
     * Test for expected coordination behavior from ModelImpl to InputModel instance when searching for invalid name.
     * See GIVEN @Before setup method.
     */
    @Test
    public void testInvalidCharacterName(){

        //WHEN - searching for invalid character name
        CharacterInfo info = model.getCharacterInfo("wonder-woman");

        //THEN - expect InputModel method to be triggered
        verify(input, times(1)).getInfoByName("wonder-woman");

        //THEN - expect null return returned
        assertNull(info);
    }

    /**
     * Testing InputModel's getInfoByName() invalid name search
     * Testing a one layer down ModelFacade - specifically for OnlineMarvelModel's interaction with
     * the concrete class MarvelApiHandler which is responsible for sending requests that hits the web API.
     *
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
        CharacterInfo info = model.getCharacterInfo("spider-man");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("spider-man");

        assertNotNull(input.getInfoByName("spider-man"));

        assertEquals(spiderman.getName(), info.getName());
        assertEquals(spiderman.getDescription(), info.getDescription());
        assertEquals(spiderman.getModified(), info.getModified());

    }

    /**
     *
     * Testing InputModel's getInfoByName() invalid name search
     * Testing a one layer down ModelFacade - specifically for OnlineMarvelModel's interaction with
     * the concrete class MarvelApiHandler which is responsible for sending requests that hits the web API.
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
        CharacterInfo info = model.getCharacterInfo("invalid");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("invalid");
        assertNull(info);

    }

    /**
     * Testing expected CharacterInfo returned from input model when searching
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
        CharacterInfo info = model.getCharacterInfo("spider-man");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("spider-man");

        assertNotNull(input.getInfoByName("spider-man"));

        assertEquals(spiderman.getName(), info.getName());
        assertEquals(spiderman.getDescription(), info.getDescription());
        assertEquals(spiderman.getModified(), info.getModified());
        assertNull(info.getThumbnail());
        assertNull(info.getStoryList());
    }

    /**
     * Testing Image object for thumbnail display created from provided CharacterInfo object.
     *
     * Test using mocked InputModel, testing behviour between ModelFacade and InputModel
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
     * Testing image path for dummy intput API model for thumbnail display created from provided CharacterInfo object.
     *
     */
    @Test
    public void testInputModelGetThumbnailFullPath(){
        //uses concrete model
        input = new OfflineMarvelModel();

        //GIVEN
        model = new ModelImpl(input, output, configFilePath);

        //WHEN
        CharacterInfo info = model.getCharacterInfo("spider-man");
        //THEN
        assertNotNull(info);
        assertEquals(2222, info.getId());

        //WHEN
        String path = model.getImagePathByInfo(info);
        //THEN
        assertEquals("./src/main/resources/marvel/dummy.png", path);

    }

    /**
     * Test for getImageByInfo behaviour when CharacterInfo object has null thumbnail attribute.
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
     * Testing ModelFacade sendReport behavior for valid and invalid CharacterInfo passed in.
     */
    @Test
    public void testSendReportFacade(){
        //GIVEN
        when(output.sendReport(spiderman)).thenReturn(true);
        when(output.sendReport(null)).thenReturn(false);
        //WHEN
        boolean ret = model.sendReport(spiderman);
        //THEN
        assertTrue(ret);
        verify(output, times(1)).sendReport(spiderman);
        //WHEN
        ret = model.sendReport(null);
        //THEN
        assertFalse(ret);

    }

    /**
     * Testing output model's getReportUrl() triggered when model facade's getReportUrl is called.
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
     * Testing sendReport() one layer below ModelFacade, using instance of OnlinePastebinModel.
     *
     * Testing with mock PastebinApiHandler, testing interaction between OnlinePastebinModel and PastebinHandler.
     * Test that PastebinApiHandler's sendReport() is called when ModelFacade's sendReport() is called
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
        boolean ret = model.sendReport(spiderman);
        //THEN
        verify(handler, times(1)).sendReport(anyString(), anyString());

    }

    /**
     * Testing ModelFacade's getReportUrl() interaction with OutputModel
     *
     * Testing getReportUrl() one layer below ModelFacade, using instance of OnlinePastebinModel.
     * Testing with mock PastebinApiHandler, testing interaction between OnlinePastebinModel and PastebinHandler.
     * Test that PastebinApiHandler's sendReport() is called when ModelFacade's sendReport() is called
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
     * REFACTOR RED
     * added addObserver(), notifyObserverGet/SendComplete() to ModelFacade
     * added new interface ModelObserver
     * added updateCharacterInfo(), updateReportUrl() in ModelObserver
     * MainPresenter implements ModelObserver
     *
     */
    @Test
    public void testRefactoredObserverUpdateGetCharacterInfoComplete(){

    }

    @Test
    public void testRefactoredObserverUpdateSendReportComplete(){

    }

}
