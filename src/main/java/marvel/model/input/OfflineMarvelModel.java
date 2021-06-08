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
    private static Connection connection = null;
    private boolean isInfoInCache;
    private String dummyResponseFilePath = "./src/main/resources/marvel/DummyApiResponse.json";
    private ResponseHandler responseHandler;

    /**
     * Constructor for OfflineMarvelModel
     */
    public OfflineMarvelModel(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MarvelCache.sqlite");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
                saveToCache(info.getName(), dummyResponse);
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
        String response = loadFromCache(name);
        System.out.println(response);

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

    private void saveToCache(String name, String response){

        String query = "INSERT INTO Character (Name, Response) \nVALUES(?, ?)";

        try{
            PreparedStatement pr = connection.prepareStatement(query);
            System.out.println("saved to cache : " + name);
            pr.setString(1, name);
            pr.setString(2, response);
            pr.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String loadFromCache(String name){
        System.out.println("CacheHandler loadFromCache");
        String query = "SELECT * from Character where Name = ?;";

        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, name);

            ResultSet rs = pr.executeQuery();

            if(rs.next()){
                System.out.println("Result set");
                System.out.println(rs.getString("Response"));
                return rs.getString("Response");
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
