package marvel.model.input;

import marvel.model.character.CharacterInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseHandler {

    public CharacterInfo parseResponseBody(String body){
        try{
            JSONObject response = new JSONObject(body);
            System.out.println(response.toString());
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

    public CharacterInfo parseCharacterInfo(String body){
        try{
            JSONObject response = new JSONObject(body);
            JSONArray results = response.getJSONArray("results");
            for(int i = 0 ; i < results.length() ; i++){
                JSONObject character = results.getJSONObject(i);
                
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

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
