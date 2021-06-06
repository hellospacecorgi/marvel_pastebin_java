package marvel.model.output;
import marvel.model.input.OnlineMarvelModel;
import marvel.model.input.ResponseHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * API handler that handles sending and retrieving requests to the Pastebin web API.
 *
 * @see OnlinePastebinModel
 *
 */
public class PastebinApiHandler {
    /**
     * User's Pastebin API developer public key
     */
    private String publicKey;
    /**
     * HttpClient used to send and retrieve response
     */
    private HttpClient client;

    /**
     * URL to paste generated for last report sent out
     */
    private String lastOutputUrl;

    /**
     * Constructor for PastebinApiHandler
     * takes in public key and private key parsed from ConfigKey.json file.
     *
     *
     * @param publicKey User's Pastebin API developer public key
     * @throws NullPointerException if public key and private keys are null
     * @throws IllegalArgumentException if public key and private keys are empty
     */
    public PastebinApiHandler(String publicKey){
        if(publicKey == null){
            throw new NullPointerException();
        }
        if(publicKey.isEmpty()){
            System.out.println("Output API key is empty - check config file");
            throw new IllegalArgumentException();
        }
        this.publicKey = publicKey;
        client = HttpClient.newHttpClient();
    }

    /**
     * Sends POST request to Pastebin web API to create paste containing given report.
     *
     *  @param name - Name of character that the report is about
     * @param report - String of report data to create paste with
     * @return boolean - return true if paste sent and URL to paste retrieved, otherwise false
     */
    public boolean sendReport(String name, String report){
        System.out.println("Report : " + report);

        Map<Object, Object> bodyData = new HashMap<>();
        bodyData.put("api_dev_key", this.publicKey);
        bodyData.put("api_option", "paste");
        bodyData.put("api_paste_name", name.concat(" Report"));
        bodyData.put("api_paste_code", report);

        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Object, Object> e : bodyData.entrySet()){
            if(sb.length() > 0){
                //next parameter
                sb.append("&");
            }
            sb.append(URLEncoder.encode(e.getKey().toString(), StandardCharsets.UTF_8));
            sb.append("=");
            sb.append(URLEncoder.encode(e.getValue().toString(), StandardCharsets.UTF_8));
        }

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(sb.toString()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create("https://pastebin.com/api/api_post.php"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() < 400){
                System.out.println(response.body());
                this.lastOutputUrl = response.body();
                return true;
            }
            System.out.println(response.statusCode() + " " + response.body());
            this.lastOutputUrl = null;
            return false;
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves URL to paste generated for last report sent
     *
     * @return String - URL to paste generated for last report sent
     */
    public String getOutputUrl(){
        return this.lastOutputUrl;
    }

}
