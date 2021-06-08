package marvel.model.input;

import marvel.model.character.*;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy version implementation of InputModel. Returns dummy data on mutable and accessor calls.
 *
 * @see InputModel
 */
public class OfflineMarvelModel implements InputModel{
    private CacheHandler cacheHandler;
    private String dummyResponseFilePath = "./src/main/resources/marvel/DummyApiResponse.json";
    private ResponseHandler responseHandler;

    /**
     * Constructor for OfflineMarvelModel
     */
    public OfflineMarvelModel(){
    }
    /**
     * Set a API handler to this model.
     * Inherited from InputModel interface - no usage in offline model.
     * @param handler MarvelApiHandler that handles live API requests - ignored
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

    @Override
    public void setCacheHandler(CacheHandler handler) {

    }

    /**
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
     * Use cached response with key matching given name to create CharacterInfo object.
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
