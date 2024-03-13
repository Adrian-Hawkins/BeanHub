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
    private final String mainURL = System.getenv("BEANHUB_API_URL");
    private Recipe[] userRecipes;
    private final Scanner scanner;
    private final String accessToken;

    public ViewPastRecipes(String userName, String accessToken) throws IOException, InterruptedException{
        this.accessToken = accessToken;
        scanner = new Scanner(System.in);
        String url = mainURL + "/api/viewpastrecipes/getUserRecipes?username="+userName;
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Authorization", "Bearer " + this.accessToken)
            .GET()
            .build();
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
            userRecipes[i] = new Recipe(
            currJsonObject.get("recipeId").getAsInt(),
            currJsonObject.get("recipeName").getAsString(),
            currJsonObject.get("recipeShortDescription").getAsString(),
            currJsonObject.get("prepTime").getAsInt(),
            currJsonObject.get("cookingTime").getAsInt(),
            currJsonObject.get("recipeSteps").getAsString());
        }
    }

    public void UserInteraction() throws IOException, InterruptedException{
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
                Colors.printColor(Colors.WHITE_BOLD, "Select what you want to do with this recipe:");
                String[] currOptions = {"Edit recipe", "Delete recipe", "Back to home"};
                String[] currColors = {Colors.GREEN_BRIGHT, Colors.RED, Colors.WHITE_BRIGHT};
                
                for (int i=0;i<currOptions.length;i++){
                    Colors.printColor(currColors[i], (i+1) + ": " + currOptions[i]);
                }

                temp = scanner.nextLine();
                int viewRecipeOption = 0;

                try {
                    viewRecipeOption = Integer.parseInt(temp);
                    if (viewRecipeOption>highNumber + currOptions.length || viewRecipeOption<0){
                        Integer.parseInt("q");
                    }
                } catch (NumberFormatException e) {
                    Colors.printColor(Colors.RED, "Invalid input provided, returning home.");
                    return;
                }

                switch (viewRecipeOption) {
                    case 1:
                        //Edit the recipe here
                        break;
                    case 2:
                        // Delete the recipe if no ratings are there
                        boolean recipeDeleted = deleteRecipe(userRecipes[userOption-1], this.accessToken);
                        if (recipeDeleted){
                            Colors.printColor(Colors.GREEN_BOLD_BRIGHT, "Recipe deleted successfully!");
                        } else {
                            Colors.printColor(Colors.RED_BACKGROUND_BRIGHT, "Recipe cannot be deleted!!!");
                        }
                    default:
                        return; // returns to the main page.
                }
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

    private boolean deleteRecipe(Recipe recipeToDelete, String accessToken) throws IOException, InterruptedException{
        String url = mainURL + "/api/viewpastrecipes/deleteRecipe?recipeID="+recipeToDelete.getRecipeID();
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Authorization", "Bearer " + this.accessToken)
        .DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        var ans = gson.fromJson(response.body(), Boolean.class);
        return ans;
    }
}
