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
    public void setApiHandlerKey(String apiKey) {
        this.apiHandler.setKey(apiKey);
    }

    @Override
    public CharacterInfo getInfoByName(String name) {
        String result = apiHandler.getCharacterInfoByName(name);
        CharacterInfo info = responseHandler.parseResponseBody(result);
        return info;
    }

    @Override
    public Image getThumbnailImage(String imagePath) {
        return null;
    }
}
