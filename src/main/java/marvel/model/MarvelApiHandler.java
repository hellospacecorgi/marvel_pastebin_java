package marvel.model;

import marvel.model.character.CharacterInfo;
import marvel.model.input.ResponseHandler;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class MarvelApiHandler {
    private String apiKey;
    private HttpClient client = HttpClient.newHttpClient();
    private ResponseHandler handler = new ResponseHandler();
    public MarvelApiHandler(){

    }

    public String generateHash(){
        if(apiKey != null){
            String ts = "1359";
            String combined = ts.concat("6480971221bbbb81b20b3ae5a50d3093b463c62f").concat(apiKey);

            String hashValue = DigestUtils.md5Hex(combined);
            System.out.println(hashValue);
            return hashValue;
        }
        return null;
    }

    public void setKey(String apiKey) {
        if(apiKey == null){
            throw new NullPointerException();
        }
        if(apiKey.isEmpty()){
            System.out.println("Input API key is empty - check config file");
            throw new IllegalArgumentException();
        }
        this.apiKey = apiKey;
    }

    public CharacterInfo getCharacterInfoByName(String name){
        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json")
                    .uri(URI.create("https://gateway.marvel.com/v1/public/characters".concat("?name=").concat(name)
                            .concat("&ts=1359")
                            .concat("&apikey=").concat(this.apiKey)
                            .concat("&hash=").concat(generateHash())))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() > 400){
                System.out.println("Error searching for character info\n");
                return null;

            } else if(response.statusCode() == 200){
                return handler.parseCharacterInfo(response.body());
            }
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}
