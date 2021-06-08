package marvel;

import marvel.model.*;
import marvel.model.character.CharacterInfo;
import marvel.model.character.ResourceUrl;
import marvel.model.character.Thumbnail;
import marvel.model.input.InputModel;
import marvel.model.output.OfflinePastebinModel;
import marvel.model.output.OutputModel;
import marvel.model.output.ReportService;
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
     * Testing that input model's getThumbnailFullPath() won't be called if CharacterInfo passed in is null
     */
    @Test
    public void testGetImagePathNullInfo(){
        //GIVEN
        when(input.getThumbnailFullPath(spiderman)).thenReturn("failed");

        //WHEN
        String path = model.getImagePathByInfo(null);

        //THEN
        verify(input, times(0)).getThumbnailFullPath(spiderman);
        assertNull(path);
    }

    /**
     * Test getCharacterInfo on empty or null name passed in
     */
    @Test
    public void testGetCharacterInfoExceptions(){
        //GIVEN
        when(input.getInfoByName(anyString())).thenReturn(spiderman);

        //WHEN
        assertThrows(NullPointerException.class, () ->{
            model.getCharacterInfo(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            model.getCharacterInfo("");
        });

        //THEN
        verify(input, times(0)).getInfoByName(anyString());
        assertNull(model.getCurrentCharacter());
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

    /**
     * Testing for return value from InputModel when there is no matching data is found
     */
    @Test
    public void testIsInfoInCacheNotFound(){
        //GIVEN
        when(input.isInfoInCache("notloaded")).thenReturn(false);

        //WHEN
        boolean status = model.isInfoInCache("notloaded");

        //THEN
        verify(input, times(1)).isInfoInCache("notloaded");
        assertFalse(status);
    }

    /**
     * Testing for return value from InputModel when there is matching data found
     */
    @Test
    public void testIsInfoInCacheFound(){
        //GIVEN
        when(input.isInfoInCache("match")).thenReturn(true);

        //WHEN
        boolean status = model.isInfoInCache("match");

        //THEN
        verify(input, times(1)).isInfoInCache("match");
        assertTrue(status);
    }

    /**
     * Testing for exceptions from ModelImpl on error cases
     */
    @Test
    public void testIsInfoInCacheExceptions(){
        //WHEN-THEN
        assertThrows(IllegalArgumentException.class, ()->{
            model.isInfoInCache(null);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            model.isInfoInCache("");
        });
    }
}
