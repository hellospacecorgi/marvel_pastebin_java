package marvel.model;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 *  Reads and stores API key as configured inside KeyConfig.json file.
 */
public class ConfigHandler {
    private String inputPublicKey = "";
    private String inputPrivateKey = "";
    private String outputKey = "";

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

    public String getInputPublicKey() {
        return inputPublicKey;
    }

    public String getInputPrivateKey() {
        return inputPrivateKey;
    }

    public String getOutputKey() {
        return outputKey;
    }
}
