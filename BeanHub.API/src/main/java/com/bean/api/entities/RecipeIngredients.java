package com.bean.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "[recipe_ingredients]")
public class RecipeIngredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeIngredientsId;

    @ManyToOne
    @JoinColumn(name = "Ingredient_ID")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "Recipe_ID")
    @JsonIgnore
    private Recipe recipe;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "Unit_ID")
    private Unit unit;

    public Long getRecipeIngredientsId() {
        return recipeIngredientsId;
    }

    public void setRecipeIngredientsId(Long recipeIngredientsId) {
        this.recipeIngredientsId = recipeIngredientsId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
