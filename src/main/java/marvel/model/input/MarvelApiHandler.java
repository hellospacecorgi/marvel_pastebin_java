package marvel.model.input;

import marvel.model.character.CharacterInfo;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

public class MarvelApiHandler {
    private ResponseHandler responseHandler;
    private String publicKey = "9809e50b4e4428f620e48572801b007f";
    private String privateKey = "6480971221bbbb81b20b3ae5a50d3093b463c62f";
    private HttpClient client;

    public MarvelApiHandler(){
        client = HttpClient.newHttpClient();
        responseHandler = new ResponseHandler();
    }

    public void setKeys(String publicKey, String privateKey){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }


    public String generateHash(){
        if(publicKey != null && privateKey != null){
            String ts = "1359";
            String combined = ts.concat(privateKey).concat(publicKey);

            String hashValue = DigestUtils.md5Hex(combined);
            System.out.println(hashValue);
            return hashValue;
        }
        return null;
    }

    /**
     * Sends GET request to Marvel API to retrieve live result for searching character information by name String.
     *
     * @param name
     * @return
     */
    public CharacterInfo getCharacterInfoByName(String name){
        if(publicKey == null || privateKey == null){
            return null;
        }

        if(publicKey.equals("") || publicKey.isEmpty() || privateKey.equals("") || privateKey.isEmpty()){
            return null;
        }

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .headers("accept", "application/json")
                    .uri(URI.create("https://gateway.marvel.com/v1/public/characters".concat("?name=").concat(name)
                            .concat("&ts=1359")
                            .concat("&apikey=").concat(this.publicKey)
                            .concat("&hash=").concat(generateHash())))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() > 400){
                System.out.println("Error searching for character info\n");
                ;
                return responseHandler.parseResponseBody(response.body());

            } else if(response.statusCode() == 200){
                return responseHandler.parseResponseBody(response.body());
            }
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }
}
