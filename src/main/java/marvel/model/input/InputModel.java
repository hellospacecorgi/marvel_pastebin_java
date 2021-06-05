package marvel.model.input;

import javafx.scene.image.Image;
import marvel.model.character.CharacterInfo;

public interface InputModel {

    public void setApiHandlerKey(String apiKey);

    public CharacterInfo getInfoByName(String name);

    public Image getThumbnailImage(String imagePath);
}
