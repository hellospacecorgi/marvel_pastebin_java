package marvel.model.input;

import marvel.model.character.CharacterInfo;

import java.sql.*;

/**
 * Online version implementation of InputModel. Returns live data retrieved from Marvel web API.
 *
 * @see MarvelApiHandler
 */
public class OnlineMarvelModel implements InputModel{

    private static Connection connection = null;
    private boolean isInfoInCache;

    /**
     *  Handler that handles requests to web API
     */
    private MarvelApiHandler apiHandler;

    /**
     *  Handler that handles processing JSON response to model objects and vice versa
     */
    private ResponseHandler responseHandler;

    /**
     * Constructor for OnlineMarvelModel
     */
    public OnlineMarvelModel(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MarvelCache.sqlite");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
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
        if(info != null){
            saveToCache(name, response);
        }
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

    /**
     * Use cached response with key matching given name to create CharacterInfo object.
     *
     * @param name To be used as key for searching record in database
     * @return CharacterInfo - object created from cached data found, return null on error or cache not found
     */
    @Override
    public CharacterInfo getInfoByNameFromCache(String name) {

        String response = loadFromCache(name);
        CharacterInfo info = responseHandler.parseResponseBody(response);

        return info;
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
