package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;

public class OnlineMarvelModel implements InputModel{

    private MarvelApiHandler apiHandler;

    public OnlineMarvelModel(){
    }

    @Override
    public void setApiHandler(MarvelApiHandler handler){
        this.apiHandler = handler;
    }

    @Override
    public CharacterInfo getInfoByName(String name) {
        CharacterInfo info = apiHandler.getCharacterInfoByName(name);
        return info;
    }

    @Override
    public Image getThumbnailImage(CharacterInfo info) {
        String path = info.getThumbnail().getPath();
        path = path.concat("/standard_large.");
        path = path.concat(info.getThumbnail().getExtension());
        return apiHandler.getImageByUrl(path);
    }
}
