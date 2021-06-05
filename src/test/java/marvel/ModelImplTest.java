package marvel;

import javafx.scene.image.Image;
import marvel.model.*;
import marvel.model.character.CharacterInfo;
import marvel.model.character.ResourceUrl;
import marvel.model.character.Thumbnail;
import marvel.model.input.InputModel;
import marvel.model.input.MarvelApiHandler;
import marvel.model.input.OnlineMarvelModel;
import marvel.model.output.OutputModel;
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

    @Before
    public void ModelImplSetUp(){
        //mock API classes
        input = mock(InputModel.class);
        output = mock(OutputModel.class);

        //inject mocks to model impl
        model = new ModelImpl(input, output, configFilePath);

        //valid character name
        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));
        Thumbnail thb = new Thumbnail("./src/main/resources/marvel/dummy.png", "png");
        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setUrls(urls);
        spiderman.setThumbnail(new Thumbnail("fake.jpg", "jpg"));
        when(input.getInfoByName("spider-man")).thenReturn(spiderman);

        //invalid character name - Not a marvel character
        when(input.getInfoByName("wonder-woman")).thenReturn(null);
    }

    @Test
    public void testConfigHandler(){
        ConfigHandler handler = new ConfigHandler("./src/main/resources/marvel/KeyConfig.json");
        assertNotEquals("", handler.getInputPublicKey());
        assertNotEquals("", handler.getInputPrivateKey());
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
        assertEquals("fake.jpg", info.getThumbnail().getPath());
        List<ResourceUrl> urls = info.getUrls();
        assertEquals(2, urls.size());
        assertEquals("dummy-url.com", urls.get(0).getUrl());
        assertEquals("wiki", urls.get(0).getType());
        assertEquals("another-dummy.com", urls.get(1).getUrl());
        assertEquals("blog", urls.get(1).getType());

    }

    @Test
    public void testInvalidCharacterName(){
        CharacterInfo info = model.getCharacterInfo("wonder-woman");

        verify(input, times(1)).getInfoByName("wonder-woman");

        assertNull(info);
    }

    @Test
    public void testInputModelGetInfoByNameValid(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);

        model = new ModelImpl(input, output, configFilePath);
        model.getInputSubModel().setApiHandler(handler);

        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));
        Thumbnail thb = new Thumbnail("./src/main/resources/marvel/dummy.png", "png");
        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setUrls(urls);
        spiderman.setThumbnail(new Thumbnail("fake.jpg", "jpg"));

        when(handler.getCharacterInfoByName("spider-man")).thenReturn(spiderman);

        CharacterInfo info = model.getCharacterInfo("spider-man");

        verify(handler, times(1)).getCharacterInfoByName("spider-man");

        assertNotNull(input.getInfoByName("spider-man"));

        assertEquals(spiderman.getName(), info.getName());
        assertEquals(spiderman.getDescription(), info.getDescription());
        assertEquals(spiderman.getModified(), info.getModified());

    }

    @Test
    public void testInputModelGetInfoByNameInvalid(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);

        model = new ModelImpl(input, output, configFilePath);
        model.getInputSubModel().setApiHandler(handler);

        when(handler.getCharacterInfoByName("invalid")).thenReturn(null);

        CharacterInfo info = model.getCharacterInfo("invalid");

        verify(handler, times(1)).getCharacterInfoByName("invalid");
        assertNull(info);

    }

    @Test
    public void testInputModelGetInfoNullList(){
        //mock marvelApiHandler
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);

        model = new ModelImpl(input, output, configFilePath);
        model.getInputSubModel().setApiHandler(handler);

        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setThumbnail(null);
        spiderman.setStoryList(null);

        when(handler.getCharacterInfoByName("spider-man")).thenReturn(spiderman);

        CharacterInfo info = model.getCharacterInfo("spider-man");

        verify(handler, times(1)).getCharacterInfoByName("spider-man");

        assertNotNull(input.getInfoByName("spider-man"));

        assertEquals(spiderman.getName(), info.getName());
        assertEquals(spiderman.getDescription(), info.getDescription());
        assertEquals(spiderman.getModified(), info.getModified());
        assertNull(info.getThumbnail());
        assertNull(info.getStoryList());
    }

    @Test
    public void testGetImageViaModelFacade(){
        //uses mock InputModel
        CharacterInfo info = model.getCharacterInfo("spider-man");
        assertEquals(info.getThumbnail().getPath(), "fake.jpg");
        assertEquals(info.getThumbnail().getExtension(), "jpg");

        //mock behavior
        Image spiderImg = null;
        try{
            spiderImg = new Image(new FileInputStream(imgPath));
            when(input.getThumbnailImage(info)).thenReturn(spiderImg);
            Image img = model.getImageByInfo(info);

            verify(input, times(1)).getThumbnailImage(info);

            assertNotNull(img);
            assertEquals(spiderImg, img);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInputModelGetThumbnailImage(){
        //uses concrete model
        input = new OnlineMarvelModel();
        MarvelApiHandler handler = mock(MarvelApiHandler.class);

        model = new ModelImpl(input, output, configFilePath);
        model.getInputSubModel().setApiHandler(handler);

        CharacterInfo spiderman = new CharacterInfo(1234, "spiderman","Can jump around buildings", "1999-99-99");
        spiderman.setStoryList(null);

        when(handler.getCharacterInfoByName("spider-man")).thenReturn(spiderman);
        CharacterInfo info = model.getCharacterInfo("spider-man");
        verify(handler, times(1)).getCharacterInfoByName("spider-man");

        spiderman.setThumbnail(new Thumbnail("fake", "jpg"));

        Image spiderImg = null;
        try{
            spiderImg = new Image(new FileInputStream(imgPath));
            when(handler.getImageByUrl("fake/standard_large.jpg")).thenReturn(spiderImg);
            Image img = model.getImageByInfo(info);

            verify(handler, times(1)).getImageByUrl("fake/standard_large.jpg");

            assertNotNull(img);
            assertEquals(spiderImg, img);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    @Test
    public void testNullThumbnailCharacter(){
        CharacterInfo dummy = new CharacterInfo(2222, "dummy","Some dummy hero", "199-23-23");
        dummy.setThumbnail(null);
        assertNull(model.getImageByInfo(dummy));

        dummy.setThumbnail(new Thumbnail("", ""));
        assertNull(model.getImageByInfo(dummy));

    }

}
