package marvel;

import marvel.model.ModelFacade;
import marvel.model.ModelImpl;
import marvel.model.ModelObserver;
import marvel.model.character.CharacterInfo;
import marvel.model.character.ResourceUrl;
import marvel.model.character.Thumbnail;
import marvel.model.input.InputModel;
import marvel.model.input.MarvelApiHandler;
import marvel.model.input.OnlineMarvelModel;
import marvel.model.output.OutputModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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


}
