package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;
import marvel.model.character.ResourceUrl;
import marvel.model.character.Thumbnail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class OfflineMarvelModel implements InputModel{
    /**
     * Inherited from InputModel interface - no usage in offline model.
     * @param handler
     */
    @Override
    public void setApiHandler(MarvelApiHandler handler){ }

    @Override
    public CharacterInfo getInfoByName(String name) {
        List<ResourceUrl> urls = new ArrayList<>();
        urls.add(new ResourceUrl("wiki", "dummy-url.com"));
        urls.add(new ResourceUrl("blog", "another-dummy.com"));
        Thumbnail thb = new Thumbnail("./src/main/resources/marvel/dummy.png", "png");
        CharacterInfo dummy = new CharacterInfo(2222, name, "A hero that is created for the sake of dummy version.", "1999-999-9999");
        dummy.setUrls(urls);
        dummy.setThumbnail(thb);
        return dummy;
    }

    @Override
    public Image getThumbnailImage(CharacterInfo info) {
        try{
            Image img = new Image(new FileInputStream("./src/main/resources/marvel/dummy.png"));
            return img;
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}
