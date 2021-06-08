package marvel.model.input;

import java.sql.*;

/**
 * The CacheHandler class is responsible for processing SQL queries to the database for cached responses.
 *
 * <p>Connects to a SQLite database named MarvelCache.sqlite,
 * which is used for caching JSON responses from previous character information searches.</p>
 *
 * <p>The database schema is a key-value pair consisting of the search name provided by the user in a previous search as the key,
 * and the JSON response containing character data from the API</p>
 *
 * <p>The database uses one table named Character which has 2 columns, <b>Name</b> and <b>Response</b></p>
 *
 * <p><b>Name</b> is a Primary key that is set to replace on conflict - only one response is stored for each search query name string.</p>
 *
 * <p>Caching is done automatically on a search that hits the API with a response that contains one character data.</p>
 *
 * @see InputModel
 * @see OnlineMarvelModel
 * @see OfflineMarvelModel
 */
public class CacheHandler {
    /**
     * Reference to the connection session to the database
     */
    public static Connection connection = null;

    /**
     * Constructor which establishes connection to the database on initialisation.
     */
    public CacheHandler(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MarvelCache.sqlite");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the provided name and response string as a record in the database
     *
     * <p>Database schema designed to overwrite existing record with the name is already a key in the cache.</p>
     *
     * @param name To be inserted as the Name value in the Character table, a key to the record
     * @param response To be inserted as the Response value in the Character table, value of the record
     */
    public void saveToCache(String name, String response){

        String query = "INSERT INTO Character (Name, Response) \nVALUES(?, ?)";

        try{
            PreparedStatement pr = connection.prepareStatement(query);
            System.out.println("Saved to cache : " + name);
            pr.setString(1, name);
            pr.setString(2, response);
            pr.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the response string cached in the database given a name key.
     *
     * @param name To be used as key to search for a matching response record in the database
     * @return String - return the cached response string if a record with Name key matching provide name is found, otherwise return null
     */
    public String loadFromCache(String name){
        String query = "SELECT * from Character where Name = ?;";

        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, name);

            ResultSet rs = pr.executeQuery();

            if(rs.next()){
                return rs.getString("Response");
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Search if there exists any matching response record in the cache database
     *
     * @param name To be used as key to search for a matching response record in the database
     * @return boolean - return true if a matching response is found, otherwise return false
     */
    public boolean isInfoInCache(String name){
        String query = "SELECT Response from Character where Name = ?;";
        try{
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, name);
            ResultSet rs = pr.executeQuery();

            if(rs.next()){
                System.out.println(name + " is in cache!");
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
