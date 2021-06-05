package marvel;

import marvel.model.*;
import marvel.model.character.CharacterInfo;
import marvel.model.input.InputModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ModelImplTest {
    ModelFacade model;
    InputModel input;
    OutputModel output;
    String configFilePath = "./src/main/resources/marvel/KeyConfig.json";

    @Before
    public void ModelImplSetUp(){
        //mock API classes
        input = mock(InputModel.class);
        output = mock(OutputModel.class);

        //inject mocks to model impl
        model = new ModelImpl(input, output, configFilePath);

        //valid character name
        List<String> urls = new ArrayList<>();
        urls.add("dummy url string");
        urls.add("another dummy url");
        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99", urls, "fake.jpg");
        when(input.getInfoByName("spider-man")).thenReturn(spiderman);

        //invalid character name - Not a marvel character
        when(input.getInfoByName("wonder-woman")).thenReturn(null);
    }

    @Test
    public void testConfigHandler(){
        ConfigHandler handler = new ConfigHandler("./src/main/resources/marvel/KeyConfig.json");
        assertNotEquals("", handler.getInputKey());
        assertNotEquals("", handler.getOutputKey());
    }

    @Test
    public void testGetSubModels(){
        assertNotNull(model.getInputSubModel());
        assertNotNull(model.getOutputSubModel());
    }

    @Test
    public void testValidCharacterName(){
        CharacterInfo info = model.getCharacterInfo("spider-man");

        verify(input, times(1)).getInfoByName("spider-man");

        assertEquals(1234, info.getId());
        assertEquals("spiderman", info.getName());
        assertEquals("fake.jpg", info.getThumbnail());
        List<String> urls = info.getUrls();
        assertEquals(2, urls.size());
        assertEquals("dummy url string", urls.get(0));
        assertEquals("another dummy url", urls.get(1));

    }

    @Test
    public void testInvalidCharacterName(){
        CharacterInfo info = model.getCharacterInfo("wonder-woman");

        verify(input, times(1)).getInfoByName("wonder-woman");

        assertNull(info);
    }

}
