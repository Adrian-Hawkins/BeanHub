package com.bean.api.requests;

public class RecipeAverageRating {
    private Long recipeId;
    private Double averageRating;

    public RecipeAverageRating(Long recipeId, Double averageRating) {
        this.recipeId = recipeId;
        this.averageRating = averageRating;
    }
    public Long getRecipeId() {
        return recipeId;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

}
