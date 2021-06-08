package marvel.model.input;

import marvel.model.character.CharacterInfo;

/**
 * Online version implementation of InputModel. Returns live data retrieved from Marvel web API.
 *
 * @see MarvelApiHandler
 */
public class OnlineMarvelModel implements InputModel{
    /**
     *  Handler that handles requests to web API
     */
    private MarvelApiHandler apiHandler;

    /**
     *  Handler that handles processing JSON response to model objects and vice versa
     */
    private ResponseHandler responseHandler;

    /**
     * Default constructor for OnlineMarvelModel
     */
    public OnlineMarvelModel(){
    }

    /**
     * Sets a handler for processing GET requests and responses to the API
     *
     * @param handler MarvelApiHandler instance
     */
    @Override
    public void setApiHandler(MarvelApiHandler handler){
        this.apiHandler = handler;
    }

    /**
     * Sets a handler for processing JSON responses to model objects and vice versa
     *
     * @param handler ResponseHandler instance
     */
    @Override
    public void setResponseHandler(ResponseHandler handler) {
        this.responseHandler = handler;
    }

    /**
     * Sends and process GET request for retrieving information about character given name.
     *
     * <p>Delegates actual sending of HttpRequest to MarvelApiHandler</p>
     *
     * @param name Specified character name to search API with
     * @return CharacterInfo - object that represents data related to a specified character if name is a valid Marvel character name, null otherwise
     */
    @Override
    public CharacterInfo getInfoByName(String name) {
        if(name == null || responseHandler == null || apiHandler == null){
            return null;
        }
        String response = apiHandler.getCharacterInfoByName(name);
        CharacterInfo info = responseHandler.parseResponseBody(response);
        return info;
    }

    /**
     * Generate the full image path for retrieving a representative image of the given character,
     * using CharacterInfo info's Thumbnail attribute.
     *
     * <p>Returns null if info's Thumbnail is null'</p>
     *
     * @param info - CharacterInfo object that contains
     * @return String - Full image path created using path and extension from Thumbnail of info if it is not null, return null otherwise.
     */
    @Override
    public String getThumbnailFullPath(CharacterInfo info) {
        if(info == null){
            return null;
        }
        if(info.getThumbnail() == null){
            return null;
        }
        if(info.getThumbnail().getPath() == null){
            return null;
        }

        String path = info.getThumbnail().getPath();
        path = path.concat("/standard_large.");
        path = path.concat(info.getThumbnail().getExtension());
        return path;
    }

    @Override
    public boolean isInfoInCache(String name) {
        return false;
    }
}
