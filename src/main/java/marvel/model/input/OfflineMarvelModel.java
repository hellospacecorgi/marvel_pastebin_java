package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.MarvelApiHandler;
import marvel.model.character.CharacterInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class OfflineMarvelModel implements InputModel{

    /**
     * Inherited from InputModel interface - no usage in offline model.
     * @param apiKey
     */
    @Override
    public void setApiHandlerKey(String apiKey) { }

    @Override
    public void setApiHandler(MarvelApiHandler handler) {
    }

    @Override
    public CharacterInfo getInfoByName(String name) {
        List<String> urls = new ArrayList<>();
        urls.add("dummy-url.com");
        urls.add("another-dummy-url.com");
        CharacterInfo dummy = new CharacterInfo(2222, name, "A hero that is created for the sake of dummy version.", "1999-999-9999");
        return dummy;
    }

    @Override
    public Image getThumbnailImage(String imagePath) {
        try{
            Image img = new Image(new FileInputStream(imagePath));
            return img;
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}
