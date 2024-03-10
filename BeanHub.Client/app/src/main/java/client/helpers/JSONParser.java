package client.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSONParser {
    private static final Gson gson = new Gson();

    public String getItem(String jsonBody, String key) {
        JsonObject jsonObject = gson.fromJson(jsonBody, JsonObject.class);
        return jsonObject.get(key).getAsString();
    }
}
