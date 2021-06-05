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
    private String inputKey = "";
    private String outputKey = "";

    public ConfigHandler(String filePath){
//        JSONParser parser = new JSONParser();
//        try {
//            Object object = parser.parse(new FileReader(filePath));
//            JSONObject jsonObject = (JSONObject) object;
//            inputKey = (String) jsonObject.get("marvelKey");
//            outputKey = (String) jsonObject.get("pastebinKey");
//
//        } catch (ParseException | IOException e) {
//            e.printStackTrace();
//        }

        System.out.println(inputKey + " " + outputKey);
    }

    public String getInputKey() {
        return inputKey;
    }

    public String getOutputKey() {
        return outputKey;
    }
}
