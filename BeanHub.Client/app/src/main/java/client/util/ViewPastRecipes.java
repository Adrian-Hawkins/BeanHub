package client.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ViewPastRecipes {
    private final int userID;
    private final int pageSize = 5;
    private int pageNumber = 1;
    private final int maxNumPages;
    private final String mainURL = "http://18.203.89.61";
    private List<Recipe> userRecipes;
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

        int numUserRecipes = jsonarr.size();
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

        this.userRecipes = new ArrayList<Recipe>();

        for (int i=0;i<numUserRecipes;i++) {
            JsonObject currJsonObject = jsonarr.get(i).getAsJsonObject();
            JsonArray recipeIngredientsJson = currJsonObject.getAsJsonArray("recipeIngredients");

            List<String> recipeIngredients = new ArrayList<String>();
            for (int j=0;j<recipeIngredientsJson.size();j++){
                JsonObject currIngredient = recipeIngredientsJson.get(j).getAsJsonObject();
                String ingredientName = currIngredient.get("ingredient").getAsJsonObject().get("ingredientName").getAsString();
                Long quantity = currIngredient.get("quantity").getAsLong();
                String unit = currIngredient.get("unit").getAsJsonObject().get("unitName").getAsString();

                String temp = ingredientName + ": " + quantity + " " + unit;
                recipeIngredients.add(temp);
            }

            userRecipes.add(new Recipe(
                    currJsonObject.get("recipeId").getAsInt(),
                    currJsonObject.get("recipeName").getAsString(),
                    currJsonObject.get("recipeShortDescription").getAsString(),
                    currJsonObject.get("prepTime").getAsInt(),
                    currJsonObject.get("cookingTime").getAsInt(),
                    currJsonObject.get("recipeSteps").getAsString()));

            userRecipes.get(i).setRecipeIngredients(recipeIngredients);
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
            if (highNumber >  userRecipes.size()){
                highNumber = userRecipes.size();
            }

            for (int i=lowNumber;i<highNumber;i++){
                System.out.println(Colors.WHITE_BOLD + (i-lowNumber+1) + ": " + Colors.RESET +
                "View " + userRecipes.get(i).getRecipeName());
            }

            for (int i=0;i<userOptions.length;i++){
                System.out.println(Colors.WHITE_BOLD + (highNumber - lowNumber + i + 1) + ": " + Colors.RESET + userOptions[i]);
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
                // System.out.println(userRecipes.get(userOption - 1));
                //View that recipe
                System.out.println(userRecipes.get(lowNumber + userOption - 1));
                Colors.printColor(Colors.WHITE_BOLD, "Select what you want to do with this recipe:");
                String[] currOptions = {"Edit recipe", "Delete recipe", "Rate recipe", "Back to home"};
                String[] currColors = {Colors.GREEN_BRIGHT, Colors.RED, Colors.PURPLE, Colors.WHITE_BRIGHT};
                
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
                        Long recID = (long) userRecipes.get(userOption - 1).getRecipeID();
                        
                        String newName = "";

                        while (newName.length() < 1){
                            Colors.printColor(Colors.WHITE_BOLD, "Enter a new recipe name: ");
                            newName = scanner.nextLine();
                        }

                        EditRecipe editingRecipe = new EditRecipe("http://18.203.89.61");
                        editingRecipe.edit(recID, newName, this.accessToken);
                        Colors.printColor(Colors.GREEN_BOLD_BRIGHT, "Recipe edited successfully!");
                        return;
                    case 2:
                        // Delete the recipe if no ratings are there
                        boolean recipeDeleted = deleteRecipe(userRecipes.get(userOption - 1), this.accessToken);
                        if (recipeDeleted){
                            Colors.printColor(Colors.GREEN_BOLD_BRIGHT, "Recipe deleted successfully!");
                        } else {
                            Colors.printColor(Colors.RED_BACKGROUND_BRIGHT, "Recipe cannot be deleted!!!");
                        }
                        return;
                    case 3:
                        // rate the recipe here
                        RateRecipe ratingRecipe = new RateRecipe();

                        ratingRecipe.rate((long) userRecipes.get(userOption - 1).getRecipeID(),
                                            (long) this.userID,
                                            this.accessToken);
                        return;
                    default:
                        return; // returns to the main page.
                }
            } else {
                if (userOption==highNumber-lowNumber+1){
                    if (pageNumber==1){
                        pageNumber = maxNumPages;
                    } else {
                        pageNumber--;
                    }
                }else if (userOption==highNumber-lowNumber+2){
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
