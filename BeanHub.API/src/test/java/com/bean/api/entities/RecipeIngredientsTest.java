package com.bean.api.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeIngredientsTest {

    @InjectMocks
    private RecipeIngredients recipeIngredients;

    @Mock
    private Ingredient ingredient;

    @Mock
    private Recipe recipe;

    @Mock
    private Unit unit;

    @BeforeEach
    public void setUp() {
        recipeIngredients = new RecipeIngredients();
    }

    @Test
    public void testRecipeIngredientsId() {
        recipeIngredients.setRecipeIngredientsId(1);
        assertEquals(1, recipeIngredients.getRecipeIngredientsId());
    }

    @Test
    public void testIngredient() {
        recipeIngredients.setIngredient(ingredient);
        assertEquals(ingredient, recipeIngredients.getIngredient());
    }

    @Test
    public void testRecipe() {
        recipeIngredients.setRecipe(recipe);
        assertEquals(recipe, recipeIngredients.getRecipe());
    }

    @Test
    public void testQuantity() {
        recipeIngredients.setQuantity(100);
        assertEquals(100, recipeIngredients.getQuantity());
    }

    @Test
    public void testUnit() {
        recipeIngredients.setUnit(unit);
        assertEquals(unit, recipeIngredients.getUnit());
    }
}
