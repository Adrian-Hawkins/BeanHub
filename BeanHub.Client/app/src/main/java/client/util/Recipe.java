package client.util;

public class Recipe{
    private String recipeName;
    private String recipeShortDescription;
    private Integer prepTime;
    private Integer cookingTime;
    private String recipeSteps;

    
    @Override
    public String toString() {
        return Colors.WHITE_BOLD + recipeName + Colors.RESET +
        "\nShort Description=" + recipeShortDescription +
        "\nPrep Time="+ prepTime + " minutes." +
        "\nCooking Time=" + cookingTime + "minutes." +
        "\n" + Colors.WHITE_UNDERLINED + "Recipe Steps:" + Colors.RESET + recipeSteps;
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


    public Recipe(String recipeName, String recipeShortDescription, Integer prepTime, Integer cookingTime,
            String recipeSteps) {
        this.recipeName = recipeName;
        this.recipeShortDescription = recipeShortDescription;
        this.prepTime = prepTime;
        this.cookingTime = cookingTime;
        this.recipeSteps = recipeSteps;
    }
}