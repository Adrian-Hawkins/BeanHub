package client.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
    private Recipe[] userRecipes;
    private final Scanner scanner;

    public ViewPastRecipes(String userName) throws IOException, InterruptedException{
        scanner = new Scanner(System.in);
        String url = mainURL + "getUserRecipes?username="+userName;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonArray jsonarr = gson.fromJson(response.body(), JsonArray.class);

        int numUserRecipes = jsonarr.size(); // SQL is: SELECT COUNT Recipe_ID from Recipe WHERE User_ID = userID
        if (numUserRecipes <= 0){
            Colors.printColor(Colors.RED_BOLD, "UPLOAD A RECIPE FIRST");
            this.maxNumPages = 0;
            this.userID = -1;
            return;
        } else {
            int temp = 0;
            if (numUserRecipes % pageSize != 0){
                temp = numUserRecipes/pageSize + 1;
            } else {
                temp = numUserRecipes/pageSize; 
            }
            this.maxNumPages = temp;
        }

        int uID = jsonarr.get(0)
        .getAsJsonObject().get("user")
        .getAsJsonObject().get("userId").getAsInt();
        this.userID = uID;

        userRecipes = new Recipe[numUserRecipes];
        for (int i=0;i<numUserRecipes;i++) {
            JsonObject currJsonObject = jsonarr.get(i).getAsJsonObject();
            userRecipes[i] = new Recipe(currJsonObject.get("recipeName").getAsString(),
            currJsonObject.get("recipeShortDescription").getAsString(),
            currJsonObject.get("prepTime").getAsInt(),
            currJsonObject.get("cookingTime").getAsInt(),
            currJsonObject.get("recipeSteps").getAsString());
        }
    }

    public void UserInteraction(){
        if (this.userID==-1){
            return;
        }
        while (true){
            Colors.printColor(Colors.WHITE_BOLD_BRIGHT, "Select what you want to do.");
            String[] userOptions = {"Previous page", "Next page", "Back to home"};
            String[] optionColors = {Colors.BLUE, Colors.GREEN, Colors.YELLOW};
            
            int lowNumber = (pageNumber-1) * (pageSize);
            int highNumber = pageNumber * (pageSize);
            if (highNumber >  userRecipes.length){
                highNumber = userRecipes.length;
            }

            for (int i=lowNumber;i<highNumber;i++){
                System.out.println(Colors.WHITE_BOLD + (i-lowNumber+1) + ": " + Colors.RESET +
                "View " + userRecipes[i].getRecipeName());
            }

            for (int i=0;i<userOptions.length;i++){
                System.out.println(Colors.WHITE_BOLD + (highNumber + i + 1) + ": " + Colors.RESET + userOptions[i]);
            }

            String temp = scanner.nextLine();
            int userOption = 0;
            try {
                userOption = Integer.parseInt(temp);
                if (userOption>highNumber + userOptions.length || userOption<0){
                    Integer.parseInt("q");
                }
            } catch (NumberFormatException e) {
                Colors.printColor(Colors.RED, "Select a valid option!!!");
                continue;
            }

            if (userOption<=(highNumber-lowNumber)){
                //View that recipe
                System.out.println(userRecipes[userOption-1]);
            } else {
                if (userOption==highNumber+1){
                    if (pageNumber==1){
                        pageNumber = maxNumPages;
                    } else {
                        pageNumber--;
                    }
                }else if (userOption==highNumber+2){
                    if (pageNumber==maxNumPages){
                        pageNumber = 1;
                    } else {
                        pageNumber++;
                    }
                } else {
                    return;
                }
            }
        }
    }
}
