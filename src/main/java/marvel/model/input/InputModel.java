package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;

public interface InputModel {

    public void setApiHandler(MarvelApiHandler handler);

    public CharacterInfo getInfoByName(String name);

    public Image getThumbnailImage(String imagePath);
}
