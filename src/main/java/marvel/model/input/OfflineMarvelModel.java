package marvel.model.input;

import marvel.model.character.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Dummy offline version implementation of InputModel.
 *
 * <p>Returns dummy data on search requests, will not send requests to the actual live Marvel API.</p>
 *
 * <p>Retrieval of cached data is still available,
 * this means if a valid character information response in a previous session that used the OnlineMarvelModel is cached in the database,
 * the offline version will be able to retrieve the data and return a CharacterInfo object that is representative of the valid data.</p>
 *
 * <p>Note that with the implementation of the getCharacterInfo() method,
 * dummy response is loaded from the dummy JSON file DummyApiResponse.json,
 * and will be automatically cached to the database.
 * </p>
 *
 * <p>This means that valid responses from a previous live session may be overwritten by dummy data</p>
 *
 * @see InputModel
 */
public class OfflineMarvelModel implements InputModel{
    /**
     * Handler that handles queries to the cache database
     */
    private CacheHandler cacheHandler;
    /**
     * File path to the file containing dummy JSON response on dummy character information
     */
    private String dummyResponseFilePath = "./src/main/resources/marvel/DummyApiResponse.json";
    /**
     *  Handler that handles processing JSON response to model objects
     *
     *  Used in offline model to retrieve CharacterInfo object from cached and dummy response
     */
    private ResponseHandler responseHandler;

    /**
     * Constructor for OfflineMarvelModel
     */
    public OfflineMarvelModel(){
    }
    /**
     * Set a API handler to this model.
     * Inherited from InputModel interface - no usage in offline model.
     * @param handler handler that process live API requests - ignored
     */
    @Override
    public void setApiHandler(MarvelApiHandler handler){ }

    /**
     * Set a response handler to this model.
     * @param handler handler to process JSON responses to CharacterInfo objects
     */
    @Override
    public void setResponseHandler(ResponseHandler handler){
        this.responseHandler = handler;
    }

    /**
     * Set a cache handler to this model.
     * @param handler handler to process queries to the cache database
     */
    @Override
    public void setCacheHandler(CacheHandler handler) {
        this.cacheHandler = handler;
    }

    /**
     * Retrieves a CharacterInfo object containing data about a character with the given name
     *
     * Simulates a response from successful character name search.
     * Will always return a dummy character
     *
     * @param name Specified character name to search API with
     * @return CharacterInfo - dummy CharacterInfo object with dummy data
     */
    @Override
    public CharacterInfo getInfoByName(String name) {
        if(responseHandler == null){
            return null;
        }
        try{
            String dummyResponse = Files.readString(Path.of(dummyResponseFilePath));
            if(dummyResponse != null){
                CharacterInfo info = responseHandler.parseResponseBody(dummyResponse);
                cacheHandler.saveToCache(name, dummyResponse);
                return info;
            }
        }catch(IOException e){
          e.printStackTrace();
        };

        return null;
    }
    /**
     * Load
     *
     * Uses cached response with key matching given name to create CharacterInfo object.
     *
     * @param name To be used as key for searching record in database
     * @return CharacterInfo - object created from cached data found, return null on error or cache not found
     */
    @Override
    public CharacterInfo getInfoByNameFromCache(String name) {
        String response = cacheHandler.loadFromCache(name);

        if(response != null){
            CharacterInfo info = responseHandler.parseResponseBody(response);
            return info;
        }
        return null;
    }

    /**
     * Simulates accessor call for retrieving full path to thumbnail image from CharacterInfo
     * Since this is a dummy version, return null will let Presenter load default dummy image to display as thumbnail.
     *
     * <p>If thumbnail exists for info, expects valid image path (no dummy path)</p>
     *
     * <p>If info has no thumbnail, return null</p>
     *
     * @param info CharacterInfo object that contains
     * @return String - Full URL path to thumbnail, otherwise return null
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
        if(path.equals("dummy")){
            return null;
        }
        path = path.concat("/standard_large.");
        path = path.concat(info.getThumbnail().getExtension());
        return path;
    }

    @Override
    public boolean isInfoInCache(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        return cacheHandler.isInfoInCache(name);
    }

}
