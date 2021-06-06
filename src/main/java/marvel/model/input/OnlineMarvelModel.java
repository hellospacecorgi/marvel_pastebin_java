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
     * Default constructor for OnlinemarvelModel
     */
    public OnlineMarvelModel(){
    }

    /**
     * Sets a handler for processing GET requests and responses to the API
     *
     * @param handler - MarvelApiHandler instance
     */
    @Override
    public void setApiHandler(MarvelApiHandler handler){
        this.apiHandler = handler;
    }
    /**
     * Sends and process GET request for retrieving information about character given name.
     *  Delegates actual sending of HttpRequest to MarvelApiHandler
     *
     * @param name Specified character name to search API with
     * @return CharacterInfo - object that represents data related to a specified character if name is a valid Marvel character name, null otherwise
     */
    @Override
    public CharacterInfo getInfoByName(String name) {
        CharacterInfo info = apiHandler.getCharacterInfoByName(name);
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
}
