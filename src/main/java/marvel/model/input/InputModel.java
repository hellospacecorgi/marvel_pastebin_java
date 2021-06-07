package marvel.model.input;

import marvel.model.character.CharacterInfo;

/**
 *
 * Sub model interface for methods relating to interacting with the input Marvel API.
 *
 * @see OnlineMarvelModel
 * @see OfflineMarvelModel
 * @see marvel.model.ModelImpl
 */
public interface InputModel {
    /**
     * Sets a handler for processing GET requests and responses to the API
     *
     * @param handler - MarvelApiHandler instance
     */
    public void setApiHandler(MarvelApiHandler handler);

    /**
     * Sends and process GET request for retrieving information about character given name.
     *
     * @param name Specified character name to search API with
     * @return CharacterInfo - object that represents data related to a specified character if name is a valid Marvel character name, null otherwise
     */
    public CharacterInfo getInfoByName(String name);

    /**
     * Generate the full image path for retrieving a representative image of the given character,
     * using CharacterInfo info's Thumbnail attribute.
     *
     * <p>Returns null if info's Thumbnail is null</p>
     *
     * @param info CharacterInfo object that contains
     * @return String - Full image path created using path and extension from Thumbnail of info if it is not null, return null otherwise.
     */
    public String getThumbnailFullPath(CharacterInfo info);
}
