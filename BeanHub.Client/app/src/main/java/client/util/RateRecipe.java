package client.util;

import client.helpers.JSONParser;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class RateRecipe {
    private final Scanner scanner;
    private final String BASE_URL = "http://18.203.89.61";
    private final static JSONParser jsonParser = new JSONParser();

    public RateRecipe() {
        this.scanner = new Scanner(System.in);
    }

    public void rate(Long recipeID, Long userID, String accessToken) throws IOException, InterruptedException{
        int rating = 0;

        while (rating<1 || rating > 5){
            Colors.printColor(Colors.CYAN, "Enter your rating (1 - 5):");
            String temp = scanner.nextLine();
            try {
                rating = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                Colors.printColor(Colors.RED_BACKGROUND_BRIGHT, "PLEASE ENTER A VALID INPUT!!!");
            }
        }

        // Now rate the recipe
        String url = BASE_URL + "/recipe/rate/"+recipeID+"/"+rating+"/"+userID;


        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("recipeID", recipeID);
        requestBody.put("userID", userID);
        requestBody.put("newRating", rating);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(requestBody);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .header("Authorization", "Bearer " + accessToken)
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
        .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String ans = gson.fromJson(response.body(), String.class);
        Colors.printColor(Colors.PURPLE_BOLD_BRIGHT, ans + " recipe rating!!!");
    }
}
