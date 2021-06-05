package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.MarvelApiHandler;
import marvel.model.character.CharacterInfo;

public class OnlineMarvelModel implements InputModel{
    private ResponseHandler responseHandler;
    private MarvelApiHandler apiHandler;

    public OnlineMarvelModel(){
        responseHandler = new ResponseHandler();
        apiHandler = new MarvelApiHandler();

    }


    @Override
    public void setApiHandler(MarvelApiHandler handler) {
    }

    @Override
    public void setApiHandlerKey(String apiKey) {
    }

    @Override
    public CharacterInfo getInfoByName(String name) {
        return null;
    }

    @Override
    public Image getThumbnailImage(String imagePath) {
        return null;
    }
}
