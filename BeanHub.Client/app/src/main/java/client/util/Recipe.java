package client.util;

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

    
    @Override
    public String toString() {
        return Colors.WHITE_BOLD + recipeName + Colors.RESET + "\n" + 
               Colors.WHITE_UNDERLINED + "Short Description:\n" + recipeShortDescription + "\n" + 
               Colors.WHITE_UNDERLINED + "Prep Time: \n"+ prepTime + " minutes." + "\n" + 
               Colors.WHITE_UNDERLINED + "Cooking Time:\n" + cookingTime + " minutes." + "\n" + 
               Colors.WHITE_UNDERLINED + "Recipe Steps:\n" + Colors.RESET + recipeSteps;
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
    }
}