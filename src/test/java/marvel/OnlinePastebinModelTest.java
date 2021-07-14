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
import marvel.model.output.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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
     * Reference to a character object used in tests
     */
    CharacterInfo hulk;
    /**
     * Reference to a character object used in tests
     */
    CharacterInfo groot;
    /**
     * Reference to a character object used in tests
     */
    CharacterInfo loki;

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
        spiderman = new CharacterInfo(1234, "spider-man","Can jump around buildings", "1999-99-99");
        spiderman.setUrls(urls);
        spiderman.setThumbnail(new Thumbnail("fakepath", "jpg"));
        hulk = new CharacterInfo(324, "hulk","Very mad", "1999-99-99");
        groot = new CharacterInfo(34, "groot","Am groot", "1999-99-99");
        loki = new CharacterInfo(114, "loki","Evil", "1999-99-99");

        //GIVEN searching for valid character name search triggered will return valid CharacterInfo built from data
        when(input.getInfoByName("spider-man")).thenReturn(spiderman);
        when(input.getInfoByName("hulk")).thenReturn(hulk);
        when(input.getInfoByName("groot")).thenReturn(groot);
        when(input.getInfoByName("loki")).thenReturn(loki);

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
     *
     * <p>Verify report string passed to handler includes unmatched names in brackets using ArgumentCaptor</p>
     */
    @Test
    public void testOutputModelSendReport(){

        //GIVEN
        output = new OnlinePastebinModel();
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        ReportService service = new ReportService();
        output.setReportService(service);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        //WHEN
        model.getCharacterInfo("spider-man");
        model.getCharacterInfo("hulk");
        model.getCharacterInfo("groot");
        model.setIndexSelected(0);
        model.getCharacterInfo("spider-man");
        model.sendReport(spiderman);
        //THEN
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(handler, times(1)).sendReport(anyString(), captor.capture());
        String capturedReport = captor.getValue();
        assertTrue(capturedReport.contains("(hulk) (groot)"));

    }

    /**
     * Testing size and content of unmatched names list passed to ReportService in the case when searched list only has 1 name
     * verify that generateReport is called with unmatched names list that has size of 0 (empty)
     */
    @Test
    public void testReportServiceGenerateReportArgsOneInList(){

        //GIVEN
        output = new OnlinePastebinModel();
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        ReportService service = mock(ReportService.class);
        output.setReportService(service);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        //WHEN
        model.getCharacterInfo("spider-man");
        model.sendReport(spiderman);
        //THEN
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(service, times(1)).generateReport(eq(spiderman), captor.capture());
        List<String> capturedList = captor.getValue();
        assertEquals(0, capturedList.size());

    }

    /**
     * Testing size and content of unmatched names list passed to ReportService in the case when searched list has 2 names
     * verify that generateReport is called with unmatched names list that has size of 1,
     * and the unmatched name is the one that is not at the matching index
     */
    @Test
    public void testReportServiceGenerateReportArgsTwoInList(){

        //GIVEN
        output = new OnlinePastebinModel();
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        ReportService service = mock(ReportService.class);
        output.setReportService(service);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        //WHEN
        model.getCharacterInfo("hulk");
        model.getCharacterInfo("spider-man");
        model.sendReport(spiderman);
        //THEN
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(service, times(1)).generateReport(eq(spiderman), captor.capture());
        List<String> capturedList = captor.getValue();
        assertEquals(1, capturedList.size());
        assertEquals("hulk", capturedList.get(0));

    }

    /**
     * Testing size and content of unmatched names list passed to ReportService in the case when searched list has 3 names
     * verify that generateReport is called with unmatched names list that has size of 2,
     * and the unmatched names are the ones that are not at the matching index
     */
    @Test
    public void testReportServiceGenerateReportArgsThreeInList(){

        //GIVEN
        output = new OnlinePastebinModel();
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        ReportService service = mock(ReportService.class);
        output.setReportService(service);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        //WHEN
        model.getCharacterInfo("hulk");
        model.getCharacterInfo("groot");
        model.getCharacterInfo("spider-man");
        model.sendReport(spiderman);
        //THEN
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(service, times(1)).generateReport(eq(spiderman), captor.capture());
        List<String> capturedList = captor.getValue();
        System.out.println(capturedList);
        System.out.println(model.getSearchedList());
        assertEquals(2, capturedList.size());
        assertEquals("hulk", capturedList.get(0));
        assertEquals("groot", capturedList.get(1));

    }
    /**
     * Testing size and content of unmatched names list passed to ReportService in the case when searched list has 3 names,
     * and the searched list underwent index selection and swapping a name out at the selected index
     *
     * <p>Verify that generateReport is called with unmatched names list that has size of 2,
     * and the unmatched names are the ones that are not at the matching index</p>
     */
    @Test
    public void testReportServiceGenerateReportArgsReplacedInList(){

        //GIVEN
        output = new OnlinePastebinModel();
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        ReportService service = mock(ReportService.class);
        output.setReportService(service);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        //WHEN
        model.getCharacterInfo("hulk");
        model.getCharacterInfo("groot");
        model.getCharacterInfo("spider-man");
        model.setIndexSelected(1);
        model.getCharacterInfo("loki");
        model.sendReport(loki);
        //THEN
        ArgumentCaptor<List<String>> captor = ArgumentCaptor.forClass(List.class);
        verify(service, times(1)).generateReport(eq(loki), captor.capture());
        List<String> capturedList = captor.getValue();
        assertEquals(2, capturedList.size());
        assertEquals("hulk", capturedList.get(0));
        assertEquals("spider-man", capturedList.get(1));

        List<String> namesList = model.getSearchedList();
        assertEquals("hulk", namesList.get(0));
        assertEquals("loki", namesList.get(1));
        assertEquals("spider-man", namesList.get(2));


    }

    /**
     * Testing expected exception thrown when a null unmatched names list is passed to sendReport()
     */
    @Test
    public void testSendReportNullUnmatchedNamesListExceptions(){
        //GIVEN
        output = new OnlinePastebinModel();
        ConfigHandler config = new ConfigHandler(configFilePath);
        model = new ModelImpl(input, output, config);
        PastebinApiHandler handler = mock(PastebinApiHandler.class);
        ReportService service = mock(ReportService.class);
        output.setReportService(service);
        output.setApiHandler(handler);
        when(handler.sendReport(anyString(), anyString())).thenReturn(true);

        //THEN
        assertThrows(IllegalArgumentException.class, () -> {
            output.sendReport(spiderman, null);
        });

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

    /**
     * Testing model interaction with service when null handler set
     */
    @Test
    public void testReportServiceGenerateReportNullHandler(){
        //GIVEN
        ConfigHandler handler = new ConfigHandler(configFilePath);
        output = new OnlinePastebinModel();
        model = new ModelImpl(input, output, handler);
        ReportService service = mock(ReportService.class);
        PastebinApiHandler pastebinHandler = mock(PastebinApiHandler.class);
        model.getOutputSubModel().setApiHandler(null);
        model.getOutputSubModel().setReportService(service);

        //WHEN-THEN
        //no api handler set - exception
        assertThrows(IllegalStateException.class, ()->{
            model.sendReport(spiderman);
        });
    }

    /**
     * Testing model interaction with service when null service passed in
     */
    @Test
    public void testReportServiceGenerateReportNullService(){
        //GIVEN
        ConfigHandler handler = new ConfigHandler(configFilePath);
        output = new OnlinePastebinModel();
        model = new ModelImpl(input, output, handler);
        ReportService service = mock(ReportService.class);
        when(service.generateReport(eq(null), anyList())).thenReturn(null);
        when(service.generateReport(eq(spiderman), anyList())).thenReturn("report");

        PastebinApiHandler pastebinHandler = mock(PastebinApiHandler.class);
        model.getOutputSubModel().setApiHandler(pastebinHandler);
        model.getOutputSubModel().setReportService(null);

        //WHEN-THEN
        //no report service set - exception
        assertThrows(IllegalStateException.class, ()->{
            model.sendReport(spiderman);
        });
    }

    /**
     * Testing model uses report service to prepare report for sending
     */
    @Test
    public void testReportServiceGenerateReportValid(){
        //GIVEN
        ConfigHandler handler = new ConfigHandler(configFilePath);
        output = new OnlinePastebinModel();
        model = new ModelImpl(input, output, handler);
        ReportService service = mock(ReportService.class);
        model.getOutputSubModel().setReportService(service);
        PastebinApiHandler pastebinHandler = mock(PastebinApiHandler.class);
        model.getOutputSubModel().setApiHandler(pastebinHandler);
        when(service.generateReport(null, null)).thenReturn(null);
        when(service.generateReport(eq(spiderman), anyList())).thenReturn("report");

        //WHEN
        model.sendReport(spiderman);

        //THEN
        verify(pastebinHandler, times(1)).sendReport(anyString(), anyString());
        verify(service, times(1)).generateReport(eq(spiderman), anyList());
    }

}
