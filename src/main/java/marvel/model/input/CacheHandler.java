package marvel.model.input;

import java.sql.*;

public class CacheHandler {
    public static Connection connection = null;
    private boolean isInfoInCache;

    public CacheHandler(){
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:MarvelCache.sqlite");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

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
