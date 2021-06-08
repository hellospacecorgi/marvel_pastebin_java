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
     * @param handler MarvelApiHandler instance
     */
    public void setApiHandler(MarvelApiHandler handler);

    /**
     * Sets a handler for processing JSON responses into model objects and vice versa.
     * @param handler ResponseHandler instance
     */
    public void setResponseHandler(ResponseHandler handler);

    /**
     * Sends and process GET request for retrieving information about character given name.
     *
     * @param name Specified character name to search API with
     * @return CharacterInfo - object that represents data related to a specified character if name is a valid Marvel character name, null otherwise
     */
    public CharacterInfo getInfoByName(String name);

    /**
     * Use given name as key to create CharacterInfo from cached response in the database
     * @param name Name of character to retrieve data for
     * @return CharacterInfo object created from cached data
     */
    public CharacterInfo getInfoByNameFromCache(String name);

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

    /**
     * Checks if given name matches a cached response in the database
     */
    public boolean isInfoInCache(String name);
}
