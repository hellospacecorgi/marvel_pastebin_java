package marvel.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 *  Reads and stores API key as configured inside KeyConfig.json file.
 *
 *  @see ModelImpl
 */
public class ConfigHandler {
    /**
     * User's developer public key for Marvel API
     */
    private String inputPublicKey = "";
    /**
     * User's developer private key for Marvel API
     */
    private String inputPrivateKey = "";
    /**
     * User's developer public key for Pastebin API
     */
    private String outputKey = "";

    /**
     * Constructor for ConfigHandler, takes in path to ConfigKey.json file
     *
     * Retrieves and stores the 3 API keys from configuration file.
     *
     * @param filePath - path to ConfigKey.json file
     */
    public ConfigHandler(String filePath){
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) object;
            inputPublicKey = (String) jsonObject.get("marvelKey");
            inputPrivateKey = (String) jsonObject.get("marvelPrivateKey");
            outputKey = (String) jsonObject.get("pastebinKey");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for the input API developer public key
     *
     * @return String - input API developer public key
     */
    public String getInputPublicKey() {
        return inputPublicKey;
    }

    /**
     * Getter for the input API developer private key
     *
     * @return String - input API developer private key
     */
    public String getInputPrivateKey() {
        return inputPrivateKey;
    }

    /**
     * Getter for the output API developer public key
     *
     * @return String - output API developer public key
     */
    public String getOutputKey() {
        return outputKey;
    }
}
