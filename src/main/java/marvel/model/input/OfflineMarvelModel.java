package marvel.model.input;

import marvel.model.character.*;
import org.json.JSONException;
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dummy version implementation of InputModel. Returns dummy data on mutable and accessor calls.
 *
 * @see InputModel
 */
public class OfflineMarvelModel implements InputModel{
    String dummyResponsePath = "./src/main/resources/marvel/DummyApiResponse.json";
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
        JSONParser parser = new JSONParser();
        CharacterInfo dummy;
        try {
            Object object = parser.parse(new FileReader(dummyResponsePath));
            JSONObject response = (JSONObject) object;
            JSONObject data = (JSONObject) response.get("data");
            JSONArray results = (JSONArray) data.get("results");
            for(int i = 0 ; i < results.size(); i++){
                JSONObject dummyCharacter = (JSONObject) results.get(i);
                dummy = new CharacterInfo(Integer.parseInt(dummyCharacter.get("id").toString()), dummyCharacter.get("name").toString(), dummyCharacter.get("description").toString(), dummyCharacter.get("modified").toString());
                JSONObject thumbnail = (JSONObject) dummyCharacter.get("thumbnail");
                dummy.setThumbnail(new Thumbnail(thumbnail.get("path").toString(), thumbnail.get("extension").toString()));

                List<ResourceUrl> urls = new ArrayList<>();
                JSONArray list = (JSONArray) dummyCharacter.get("urls");
                for(int j = 0 ; j < list.size() ; j ++){
                    JSONObject u = (JSONObject) list.get(j);
                    urls.add(new ResourceUrl(u.get("type").toString(), u.get("url").toString()));
                }
                dummy.setUrls(urls);

                List<Comic> comics = new ArrayList<>();
                JSONObject resource = (JSONObject)dummyCharacter.get("comics");
                list = (JSONArray) resource.get("items");
                for(int j = 0 ; j < list.size() ; j ++){
                    JSONObject u = (JSONObject) list.get(j);
                    comics.add(new Comic(u.get("name").toString(), u.get("resourceURI").toString()));
                }
                dummy.setNComics(Integer.parseInt(resource.get("available").toString()));


                List<Story> stories = new ArrayList<>();
                resource = (JSONObject)dummyCharacter.get("stories");
                list = (JSONArray) resource.get("items");
                for(int j = 0 ; j < list.size() ; j ++){
                    JSONObject u = (JSONObject) list.get(j);
                    stories.add(new Story(u.get("name").toString(), u.get("type").toString(), u.get("resourceURI").toString()));
                }
                dummy.setNStories(Integer.parseInt(resource.get("available").toString()));

                List<Event> events = new ArrayList<>();
                resource = (JSONObject)dummyCharacter.get("events");
                list = (JSONArray) resource.get("items");
                for(int j = 0 ; j < list.size() ; j ++){
                    JSONObject u = (JSONObject) list.get(j);
                    events.add(new Event(u.get("name").toString(), u.get("resourceURI").toString()));
                }
                dummy.setNEvents(Integer.parseInt(resource.get("available").toString()));

                List<Series> series = new ArrayList<>();
                resource = (JSONObject)dummyCharacter.get("series");
                list = (JSONArray) resource.get("items");
                for(int j = 0 ; j < list.size() ; j ++){
                    JSONObject u = (JSONObject) list.get(j);
                    series.add(new Series(u.get("name").toString(), u.get("resourceURI").toString()));
                }
                dummy.setNSeries(Integer.parseInt(resource.get("available").toString()));

                dummy.setComicList(comics);
                dummy.setStoryList(stories);
                dummy.setEventList(events);
                dummy.setSeriesList(series);

                return dummy;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
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
