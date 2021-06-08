package marvel.model.input;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

/**
 * API handler that handles sending and retrieving requests to the Marvel web API.
 *
 * @see OnlineMarvelModel
 * @see ResponseHandler
 */
public class MarvelApiHandler {
    /**
     * User's Marvel API developer public key
     */
    private String publicKey;
    /**
     * User's Marvel API developer private key
     */
    private String privateKey;
    /**
     * HttpClient used to send and retrieve response
     */
    private HttpClient client;

    /**
     * Constructor for MarvelApiHandler,
     * takes in public key and private key parsed from ConfigKey.json file.
     *
     * @param publicKey User's Marvel API developer public key
     * @param privateKey User's Marvel API developer private key
     *
     * @throws NullPointerException if public key and private keys are null
     * @throws IllegalArgumentException if public key and private keys are empty
     */
    public MarvelApiHandler(String publicKey, String privateKey){
        if(publicKey == null || privateKey == null){
            throw new NullPointerException();
        }
        if(publicKey.isEmpty() || privateKey.isEmpty()){
            System.out.println("Input API key is empty - check config file");
            throw new IllegalArgumentException();
        }
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        client = HttpClient.newHttpClient();
    }

    /**
     * Required for sending requests to Marvel web API,
     * uses MD5 to generate hash from String concatenated from timestamp + private key + public key
     *
     * Precondition: Non null public and private key strings provided via constructor.
     *
     * @return String - Return hash value String generated if keys non null, otherwise return null
     */
    public String generateHash(){
        if(publicKey != null && privateKey != null){
            String ts = "1359";
            String combined = ts.concat(privateKey).concat(publicKey);

            String hashValue = DigestUtils.md5Hex(combined);
            return hashValue;
        }
        return null;
    }
    /**
     * Sends GET request to Marvel API to retrieve live result for searching character information by name String.
     *
     * Uses ResponseHandler to parse JSON response and build CharacterInfo object
     *
     * @param name String of character name to send GET request with, no spaces within string
     * @return String - return response body from API, return null if keys are null or empty
     */
    public String getCharacterInfoByName(String name){

        if(publicKey == null || privateKey == null){
            return null;
        }

        if(publicKey.equals("") || publicKey.isEmpty() || privateKey.equals("") || privateKey.isEmpty()){
            return null;
        }
        if(name.contains(" ")){
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
                return response.body();

            } else if(response.statusCode() == 200){
                return response.body();
            }
        } catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

}
