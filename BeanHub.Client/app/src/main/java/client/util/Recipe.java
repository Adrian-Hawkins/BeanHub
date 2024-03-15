package client.util;

import java.util.ArrayList;
import java.util.List;

public class Recipe{
    private int recipeID;

    public int getRecipeID() {
        return recipeID;
    }


    private String recipeName;
    private String recipeShortDescription;
    private Integer prepTime;
    private Integer cookingTime;
    private String recipeSteps;
    private List<String> recipeIngredients;

    
    public void setRecipeIngredients(List<String> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }



    public List<String> getRecipeIngredients() {
        return recipeIngredients;
    }



    @Override
    public String toString() {
        String ingredientsList = "";
        for (int i=0;i<this.recipeIngredients.size();i++){
            ingredientsList = ingredientsList + "- " + this.recipeIngredients.get(i) + "\n";
        }

        return Colors.WHITE_BOLD + recipeName + Colors.RESET + "\n" + 
               Colors.WHITE_UNDERLINED + "Short Description:\n" + Colors.RESET + recipeShortDescription + "\n" + 
               Colors.WHITE_UNDERLINED + "Prep Time: \n"+ Colors.RESET + prepTime + " minutes." + "\n" + 
               Colors.WHITE_UNDERLINED + "Cooking Time:\n" + Colors.RESET + cookingTime + " minutes." + "\n" + 
               Colors.WHITE_UNDERLINED + "Recipe Steps:\n" + Colors.RESET + recipeSteps + "\n" + 
               Colors.WHITE_UNDERLINED + "Ingredients: \n" + Colors.RESET + ingredientsList;
    }


    public String getRecipeName() {
        return recipeName;
    }


    public String getRecipeShortDescription() {
        return recipeShortDescription;
    }


    public Integer getPrepTime() {
        return prepTime;
    }


    public Integer getCookingTime() {
        return cookingTime;
    }


    public String getRecipeSteps() {
        return recipeSteps;
    }


    public Recipe(int recipeID, String recipeName, String recipeShortDescription, Integer prepTime, Integer cookingTime,
            String recipeSteps) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeShortDescription = recipeShortDescription;
        this.prepTime = prepTime;
        this.cookingTime = cookingTime;
        this.recipeSteps = recipeSteps;
        this.recipeIngredients = new ArrayList<String>();
    }
}