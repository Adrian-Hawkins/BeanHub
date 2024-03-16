package com.bean.api.requests;

import com.bean.api.entities.Recipe;
import com.bean.api.entities.RecipeIngredients;
import com.bean.api.entities.Unit;
import com.bean.api.entities.User;

import java.util.List;

public class PostRecipeRequest {
    private User user;

    private Recipe recipe;

    private List<RecipeIngredients> recipeIngredients;
    public User getUser() {
        return user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public List<RecipeIngredients> getRecipeIngredients() {
        return recipeIngredients;
    }
}
