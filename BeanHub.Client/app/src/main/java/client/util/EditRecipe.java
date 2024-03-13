package client.util;

import client.helpers.JSONParser;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class EditRecipe {

    private final String BASE_URL = System.getenv("BEANHUB_API_URL");

    public void edit(Long id, String newName, String accessToken) throws IOException, InterruptedException {
        Map<String, Object> requestBody = new HashMap<>();
        String url = BASE_URL + "/recipe/update";
        requestBody.put("recipeId", id);
        requestBody.put("recipeName", newName);
        Gson gson = new Gson();
        String jsonBody = gson.toJson(requestBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                // WHY TF CAN I NOT MAKE PATCH REQUESTS???
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
