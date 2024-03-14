package client.util;

public class FeedExplore {
    private int recipeID;

    public int getRecipeID() {
        return recipeID;
    }

    private String recipeName;
    private String recipeShortDescription;
    private Integer prepTime;
    private Integer cookingTime;
    private String recipeSteps;
    private String date;
    private String averageRating;

    @Override
    public String toString() {
        return Colors.WHITE_BOLD + recipeName + Colors.RESET + "\n" +
                Colors.WHITE_UNDERLINED + "Short Description:\n" + recipeShortDescription + "\n" +
                Colors.WHITE_UNDERLINED + "Prep Time: \n" + prepTime + " minutes." + "\n" +
                Colors.WHITE_UNDERLINED + "Cooking Time:\n" + cookingTime + " minutes." + "\n" +
                Colors.WHITE_UNDERLINED + "Recipe Steps:\n" + recipeSteps + "\n" +
                Colors.WHITE_UNDERLINED + "Date Added:\n" + date + "\n" +
                Colors.WHITE_UNDERLINED + "Average Rating:\n" + Colors.RESET + averageRating + "\n";
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

    public String getDate() {
        return date;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public FeedExplore(int recipeID, String recipeName, String recipeShortDescription, Integer prepTime,
            Integer cookingTime,
            String recipeSteps, String date, String averageRating) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeShortDescription = recipeShortDescription;
        this.prepTime = prepTime;
        this.cookingTime = cookingTime;
        this.recipeSteps = recipeSteps;
        this.date = date;
        this.averageRating = averageRating;
    }
}