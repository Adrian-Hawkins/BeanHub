package client.util;

import client.helpers.JSONParser;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class PostRecipe {

    private final Scanner scanner;
    private final String BASE_URL = System.getenv("BEANHUB_API_URL");
    private final static JSONParser jsonParser = new JSONParser();
    public PostRecipe() {
        this.scanner = new Scanner(System.in);
    }

    public void post(String username) throws IOException, InterruptedException {
        System.out.print(Colors.PURPLE_UNDERLINED);
        Map<String, Object> requestBody = construct(username);
        String url = BASE_URL + "/postRecipe";
        Gson gson = new Gson();
        String jsonBody = gson.toJson(requestBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(jsonParser.getItem(response.body(), "status"));
        System.out.println(Colors.RESET);
    }

    public Map<String, Object> construct(String username) {
        Map<String, Object> recipe = new HashMap<String, Object>();
        recipe.put("user", getUser(username));
        recipe.put("recipe", inputRecipe());
        recipe.put("recipeIngredients", constructRecipeIngredients());
        return recipe;
    }

    public Map<String, Object> getUser(String username) {
        Map<String, Object> user = new HashMap<String, Object>();
        user.put("username", username);
        return user;
    }

    public Map<String, Object> inputRecipe() {

       System.out.println("Enter recipe name:");
       String recipeName = scanner.nextLine();

       System.out.println("Enter recipe short description:");
       String recipeShortDescription = scanner.nextLine();

       System.out.println("Enter preparation time (in minutes):");
       int prepTime = Integer.parseInt(scanner.nextLine());

       System.out.println("Enter cooking time (in minutes):");
       int cookingTime = Integer.parseInt(scanner.nextLine());

       System.out.println("Enter recipe steps:");
       String recipeSteps = scanner.nextLine();

       Map<String, Object> recipe = new HashMap<>();
       recipe.put("recipeName", recipeName);
       recipe.put("recipeShortDescription", recipeShortDescription);
       recipe.put("prepTime", prepTime);
       recipe.put("cookingTime", cookingTime);
       recipe.put("recipeSteps", recipeSteps);

       return recipe;
    }

    public List<Map<String, Object>> constructRecipeIngredients() {
        List<Map<String, Object>> recipeIngredientsList = new ArrayList<>();

        while (true) {
            System.out.println("Enter ingredient name (or type 'done' to finish):");
            String ingredientName = scanner.nextLine();
            if (ingredientName.equalsIgnoreCase("done")) {
                break;
            }

            System.out.println("Enter quantity:");
            int quantity = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter unit:");
            String unitName = scanner.nextLine();

            Map<String, Object> unit = new HashMap<>();
            unit.put("unitName", unitName);

            Map<String, Object> ingredient = new HashMap<>();
            ingredient.put("ingredientName", ingredientName);

            Map<String, Object> recipeIngredient = new HashMap<>();
            recipeIngredient.put("ingredient", ingredient);
            recipeIngredient.put("quantity", quantity);
            recipeIngredient.put("unit", unit);

            recipeIngredientsList.add(recipeIngredient);
        }

        return recipeIngredientsList;
    }
}
