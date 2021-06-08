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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy version implementation of InputModel. Returns dummy data on mutable and accessor calls.
 *
 * @see InputModel
 */
public class OfflineMarvelModel implements InputModel{
    private static Connection connection = null;
    private boolean isInfoInCache;
    private String dummyResponseFilePath = "./src/main/resources/marvel/DummyApiResponse.json";
    private ResponseHandler responseHandler;
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

                return responseHandler.parseResponseBody(dummyResponse);
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
        return null;
    }

    /**
     * Simulates accessor call for retrieving full path to thumbnail image from CharacterInfo
     * Since this is a dummy version, return null will let Presenter load default dummy image to display as thumbnail.
     *
     * @param info CharacterInfo object that contains
     * @return String - always return null in dummy version
     */
    @Override
    public String getThumbnailFullPath(CharacterInfo info) {
        return null;
    }

    @Override
    public boolean isInfoInCache(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        return searchCache(name);
    }

    private boolean searchCache(String name){
        String query = "SELECT Response from Character where Name = ?;";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, name);
            ResultSet rs = pr.executeQuery();

            if(rs.next()){
                System.out.println(name + "is in cache!");
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
