package marvel;

import marvel.model.ConfigHandler;
import marvel.model.ModelFacade;
import marvel.model.ModelImpl;
import marvel.model.ModelObserver;
import marvel.model.character.CharacterInfo;
import marvel.model.character.ResourceUrl;
import marvel.model.character.Thumbnail;
import marvel.model.input.*;
import marvel.model.output.OutputModel;
import net.bytebuddy.agent.VirtualMachine;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class OnlineMarvelModelTest {
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
     * Dummy string that represents a JSON response from API
     */
    String dummyResponseBody = "dummy json response";
    /**
     * Dummy string that represents a JSON response from API when request failed
     */
    String dummyErrorResponse = "dummy error response";

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
        ResponseHandler responseHandler = mock(ResponseHandler.class);
        CacheHandler chandler = mock(CacheHandler.class);

        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getInputSubModel().setApiHandler(handler);
        model.getInputSubModel().setResponseHandler(responseHandler);
        model.getInputSubModel().setCacheHandler(chandler);

        //GIVEN
        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));
        Thumbnail thb = new Thumbnail("./src/main/resources/marvel/dummy.png", "png");
        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setUrls(urls);
        spiderman.setThumbnail(new Thumbnail("fakepath", "jpg"));

        //GIVEN
        when(handler.getCharacterInfoByName("spider-man")).thenReturn(dummyResponseBody);
        when(responseHandler.parseResponseBody(dummyResponseBody)).thenReturn(spiderman);

        //WHEN
        model.getCharacterInfo("spider-man");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("spider-man");
        verify(responseHandler, times(1)).parseResponseBody(dummyResponseBody);
        verify(chandler, times(1)).saveToCache(anyString(), anyString());

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
        ResponseHandler responseHandler = mock(ResponseHandler.class);
        CacheHandler chandler = mock(CacheHandler.class);

        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getInputSubModel().setApiHandler(handler);
        model.getInputSubModel().setResponseHandler(responseHandler);
        model.getInputSubModel().setCacheHandler(chandler);

        //GIVEN
        when(handler.getCharacterInfoByName("invalid")).thenReturn(null);

        //WHEN
        model.getCharacterInfo("invalid");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("invalid");
        verify(responseHandler, times(0)).parseResponseBody(anyString());
        verify(chandler, times(0)).saveToCache(anyString(), anyString());

    }


    /**
     * Testing expected CharacterInfo returned from input model after search.
     *
     * <p>Handlers are mocked using Mockito.</p>
     */
    @Test
    public void testInputModelGetInfoNullList(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);
        ResponseHandler responseHandler = mock(ResponseHandler.class);
        CacheHandler chandler = mock(CacheHandler.class);

        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getInputSubModel().setApiHandler(handler);
        model.getInputSubModel().setResponseHandler(responseHandler);
        model.getInputSubModel().setCacheHandler(chandler);

        //GIVEN
        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setThumbnail(null);
        spiderman.setStoryList(null);

        //GIVEN
        when(handler.getCharacterInfoByName("spider-man")).thenReturn(dummyResponseBody);
        when(responseHandler.parseResponseBody(dummyResponseBody)).thenReturn(spiderman);
        //WHEN
        model.getCharacterInfo("spider-man");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("spider-man");

        assertEquals(spiderman.getName(), model.getCurrentCharacter().getName());
        assertEquals(spiderman.getDescription(), model.getCurrentCharacter().getDescription());
        assertEquals(spiderman.getModified(), model.getCurrentCharacter().getModified());
        assertNull(model.getCurrentCharacter().getThumbnail());
        assertNull(model.getCurrentCharacter().getStoryList());
    }

    /**
     * Test for getCharacterInfo return value and interaction when no ResponseHandler is set to InputModel
     */
    @Test
    public void testNullResponseHandlerSet(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);
        CacheHandler chandler = mock(CacheHandler.class);

        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getInputSubModel().setApiHandler(handler);
        model.getInputSubModel().setCacheHandler(chandler);
        model.getInputSubModel().setResponseHandler(null);

        //WHEN
        assertThrows(IllegalStateException.class, ()->{
            model.getCharacterInfo("dummy");
        });

        //THEN
        verify(handler, times(0)).getCharacterInfoByName("spider-man");
        verify(chandler, times(0)).saveToCache(anyString(), anyString());
        assertNull(model.getCurrentCharacter());
    }

    /**
     * Test for getCharacterInfo return value and interaction when no MarvelApiHandler is set to InputModel
     */
    @Test
    public void testNullApiHandlerSet(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        ResponseHandler handler = mock(ResponseHandler.class);
        CacheHandler chandler = mock(CacheHandler.class);

        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getInputSubModel().setApiHandler(null);
        model.getInputSubModel().setResponseHandler(handler);
        model.getInputSubModel().setCacheHandler(chandler);

        //WHEN
        assertThrows(IllegalStateException.class, ()->{
            model.getCharacterInfo("dummy");
        });

        //THEN
        verify(handler, times(0)).parseResponseBody(anyString());
        verify(chandler, times(0)).saveToCache(anyString(), anyString());
        assertNull(model.getCurrentCharacter());
    }

    @Test
    public void testNullCacheHandlerSet(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler apiHandler = mock(MarvelApiHandler.class);
        ResponseHandler handler = mock(ResponseHandler.class);

        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getInputSubModel().setApiHandler(apiHandler);
        model.getInputSubModel().setResponseHandler(handler);
        model.getInputSubModel().setCacheHandler(null);

        //WHEN
        assertThrows(IllegalStateException.class, ()->{
            model.getCharacterInfo("dummy");
        });

        //THEN
        verify(handler, times(0)).parseResponseBody(anyString());
        verify(apiHandler, times(0)).getCharacterInfoByName(anyString());
        assertNull(model.getCurrentCharacter());
    }

    /**
     * Test for return value and interactions from getCharacterInfo() when error from API
     */
    @Test
    public void testGetCharacterInfoErrorResponse(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);
        ResponseHandler responseHandler = mock(ResponseHandler.class);
        CacheHandler chandler = mock(CacheHandler.class);

        //GIVEN
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        model.getInputSubModel().setApiHandler(handler);
        model.getInputSubModel().setResponseHandler(responseHandler);
        model.getInputSubModel().setCacheHandler(chandler);

        //GIVEN
        when(handler.getCharacterInfoByName("error-string")).thenReturn(dummyErrorResponse);
        when(responseHandler.parseResponseBody(dummyErrorResponse)).thenReturn(null);

        //WHEN
        model.getCharacterInfo("error-string");

        //THEN
        verify(handler, times(1)).getCharacterInfoByName("error-string");
        verify(responseHandler, times(1)).parseResponseBody(dummyErrorResponse);
        verify(chandler, times(0)).saveToCache(anyString(), anyString());
        assertNull(model.getCurrentCharacter());
    }

}
