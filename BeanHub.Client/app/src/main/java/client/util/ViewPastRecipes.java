package client.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ViewPastRecipes {
    // Show recipes that the user has posted in pages of 5 recipes.
    // SQL Should look like this:
        // SELECT * FROM Recipe WHERE User_ID = userID ORDER BY Recipe_ID OFFSET pageSize * (pageNumber-1) ROWS FETCH NEXT pageSize ROWS ONLY;
    private final int userID;
    private final int pageSize = 5;
    private int pageNumber = 1;
    private final int maxNumPages;
    private final String mainURL = "http://localhost:8080/api/viewpastrecipes/";

    public ViewPastRecipes(String userName) throws IOException, InterruptedException{
        String url = mainURL + "getUserRecipes";
        // Call api now
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonArray jsonarr = gson.fromJson(response.body(), JsonArray.class);
        System.out.println(jsonarr);

        int numUserRecipes = 5; // SQL is: SELECT COUNT Recipe_ID from Recipe WHERE User_ID = userID
        this.userID = 2; // Get this from auth
        
        if (numUserRecipes % pageSize != 0){
            this.maxNumPages = numUserRecipes/pageSize + 1;
        } else {
            this.maxNumPages = numUserRecipes/pageSize; 
        }
    }

    public void getNextRecipes(){
        // SQL Should look like this:
        // SELECT * FROM Recipe WHERE User_ID = this.userID ORDER BY Recipe_ID OFFSET pageSize * (pageNumber-1) ROWS FETCH NEXT pageSize ROWS ONLY;
        String url = "http://localhost:8080/api/viewpastrecipes/getUserRecipes";

    }
}
