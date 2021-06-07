package marvel.model.input;

import marvel.model.character.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for handling JSON response body strings from Marvel API request results.
 *
 * @see MarvelApiHandler
 */
public class ResponseHandler {
    /**
     * Parses JSON response from get character info GET request
     *
     * @param body response body from GET request
     * @return CharacterInfo - returns CharacterInfo object built from response data if status code equals 200, otherwise null
     */
    public CharacterInfo parseResponseBody(String body){
        try{
            JSONObject response = new JSONObject(body);
            if(response.getDouble("code") == 409){
                System.out.println("Error code 409" + parseError409(body));
                return null;
            } else if(response.getInt("code") == 200){
                JSONObject data = response.getJSONObject("data");
                if(data.getInt("count") == 0){
                    //Request processed but zero results returned
                    return null;
                } else {
                    return parseCharacterInfo(body);
                }
            }
            return null;
        } catch(JSONException e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Parses JSON response body string and build CharacterInfo object from resource items.
     *
     * @param body response body from GET request
     * @return CharacterInfo - returns CharacterInfo object built from response data, returns null on failure to parse
     */
    public CharacterInfo parseCharacterInfo(String body){
        try{
            JSONObject response = new JSONObject(body);
            JSONObject data = response.getJSONObject("data");
            JSONArray results = data.getJSONArray("results");
            CharacterInfo info = null;
            for(int i = 0 ; i < results.length() ; i++){
                JSONObject character = results.getJSONObject(i);
                int id = character.getInt("id");
                String name = character.getString("name");
                String description = character.getString("description");
                String modified = character.getString("modified");
                JSONObject thumb = character.getJSONObject("thumbnail");

                Thumbnail tn = new Thumbnail(thumb.getString("path"), thumb.getString("extension"));

                //list of comics that featured this character
                JSONObject comics = character.getJSONObject("comics");
                int numComics = comics.getInt("available");
                JSONArray list = comics.getJSONArray("items");
                List<Comic> clist = new ArrayList<>();
                for(int j = 0; j < list.length() ; j ++){
                    JSONObject c = list.getJSONObject(j);
                    clist.add(new Comic(c.getString("name"), c.getString("resourceURI")));
                }

                //list of stories that which this character appears
                JSONObject stories = character.getJSONObject("stories");
                int numStories = stories.getInt("available");
                list = stories.getJSONArray("items");
                List<Story> slist = new ArrayList<>();
                for(int j = 0; j < list.length() ; j ++){
                    JSONObject c = list.getJSONObject(j);
                    slist.add(new Story(c.getString("name"), c.getString("type"), c.getString("resourceURI")));
                }

                //list of events that which this character appears
                JSONObject events = character.getJSONObject("events");
                int numEvents = events.getInt("available");
                list = events.getJSONArray("items");
                List<Event> elist = new ArrayList<>();
                for(int j = 0; j < list.length() ; j ++){
                    JSONObject c = list.getJSONObject(j);
                    elist.add(new Event(c.getString("name"), c.getString("resourceURI")));
                }

                //list of series that which this character appears
                JSONObject series = character.getJSONObject("series");
                int numSeries = series.getInt("available");
                list = series.getJSONArray("items");
                List<Series> seriesList = new ArrayList<>();
                for(int j = 0; j < list.length() ; j ++){
                    JSONObject c = list.getJSONObject(j);
                    seriesList.add(new Series(c.getString("name"), c.getString("resourceURI")));
                }

                //urls of public websites
                list = character.getJSONArray("urls");
                List<ResourceUrl> ulist = new ArrayList<>();
                for(int j = 0; j < list.length() ; j ++){
                    JSONObject c = list.getJSONObject(j);
                    ulist.add(new ResourceUrl(c.getString("type"), c.getString("url")));
                }

                info = new CharacterInfo(id, name, description, modified);
                info.setThumbnail(tn);
                info.setUrls(ulist);
                info.setComicList(clist);
                info.setSeriesList(seriesList);
                info.setEventList(elist);
                info.setStoryList(slist);
                info.setNComics(numComics);
                info.setNEvents(numEvents);
                info.setNSeries(numSeries);
                info.setNStories(numStories);
            }

            return info;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Parses response body from failed request of status code 409.
     *
     * Used to print error messages on terminal for debugging and error handling purposes.
     *
     * @param body response body from failed request of status code 409
     * @return String - returns parsed error message, returns null if failed to parse
     */
    public String parseError409(String body){
        try{
            JSONObject response = new JSONObject(body);
            return response.getString("status");

        } catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
