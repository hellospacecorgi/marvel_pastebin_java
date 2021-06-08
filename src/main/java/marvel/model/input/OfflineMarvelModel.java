package marvel.model.input;

import marvel.model.character.*;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy version implementation of InputModel. Returns dummy data on mutable and accessor calls.
 *
 * @see InputModel
 */
public class OfflineMarvelModel implements InputModel{
    private String dummyResponseFilePath = "./src/main/resources/marvel/DummyApiResponse.json";
    private ResponseHandler responseHandler;
    /**
     * Set a API handler to this model.
     * Inherited from InputModel interface - no usage in offline model.
     * @param handler MarvelApiHandler that handles live API requests - ignored
     */
    @Override
    public void setApiHandler(MarvelApiHandler handler){ }

    /**
     * Set a response handler to this model.
     * @param handler handler to process JSON responses to CharacterInfo objects
     */
    @Override
    public void setResponseHandler(ResponseHandler handler){
        this.responseHandler = handler;
    }

    /**
     * Simulates a response from successful character name search.
     * Will always return a dummy character
     *
     * @param name Specified character name to search API with
     * @return CharacterInfo - dummy CharacterInfo object with dummy data
     */
    @Override
    public CharacterInfo getInfoByName(String name) {
        if(responseHandler == null){
            return null;
        }
        try{
            String dummyResponse = Files.readString(Path.of(dummyResponseFilePath));
            System.out.println(dummyResponse);
            if(dummyResponse != null){

                return responseHandler.parseResponseBody(dummyResponse);
            }
        }catch(IOException e){
          e.printStackTrace();
        };

        return null;
    }

    /**
     * Simulates accessor call for retrieving full path to thumbnail image from CharacterInfo
     * Since this is a dummy version, return null will let Presenter load default dummy image to display as thumbnail.
     *
     * @param info CharacterInfo object that contains
     * @return String - always return null in dummy version
     */
    @Override
    public String getThumbnailFullPath(CharacterInfo info) {
        return null;
    }

}
