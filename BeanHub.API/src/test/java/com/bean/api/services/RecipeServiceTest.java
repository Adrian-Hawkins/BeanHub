package com.bean.api.services;

import com.bean.api.entities.Recipe;
import com.bean.api.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Recipe> query;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRecipe() {
        Recipe recipe = new Recipe();
        recipe.setRecipeName("Pasta Carbonara");
        recipe.setRecipeShortDescription("Classic Italian pasta dish");
        recipe.setPrepTime(15);
        recipe.setCookingTime(20);
        recipe.setRecipeSteps("1. Cook pasta...");
        recipe.setUser(new User());

        recipeService.saveRecipe(recipe);

        verify(entityManager, times(1)).persist(recipe);
    }

    @Test
    void updateRecipe() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setRecipeName("Pizza Margherita");
        recipe.setRecipeShortDescription("Traditional Neapolitan pizza");
        recipe.setPrepTime(30);
        recipe.setCookingTime(10);
        recipe.setRecipeSteps("1. Make dough...");
        recipe.setUser(new User());

        recipeService.updateRecipe(recipe);

        verify(entityManager, times(1)).merge(recipe);
    }

    @Test
    void deleteRecipe() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1);
        recipe.setRecipeName("Chocolate Cake");
        recipe.setRecipeShortDescription("Moist and rich cake");
        recipe.setPrepTime(20);
        recipe.setCookingTime(45);
        recipe.setRecipeSteps("1. Preheat oven...");
        recipe.setUser(new User());

        when(entityManager.contains(recipe)).thenReturn(true);

        recipeService.deleteRecipe(recipe);

        verify(entityManager, times(1)).remove(recipe);
    }

    @Test
    void getRecipeById() {
        Integer recipeId = 1;
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);
        recipe.setRecipeName("Grilled Salmon");
        recipe.setRecipeShortDescription("Healthy and flavorful fish dish");
        recipe.setPrepTime(10);
        recipe.setCookingTime(15);
        recipe.setRecipeSteps("1. Season salmon...");
        recipe.setUser(new User());

        when(entityManager.find(Recipe.class, recipeId)).thenReturn(recipe);

        Recipe result = recipeService.getRecipeById(recipeId);

        assertNotNull(result);
        assertEquals(recipe.getRecipeId(), result.getRecipeId());
        assertEquals(recipe.getRecipeName(), result.getRecipeName());
        assertEquals(recipe.getRecipeShortDescription(), result.getRecipeShortDescription());
        assertEquals(recipe.getPrepTime(), result.getPrepTime());
        assertEquals(recipe.getCookingTime(), result.getCookingTime());
        assertEquals(recipe.getRecipeSteps(), result.getRecipeSteps());
        assertNotNull(result.getUser());
    }

    @Test
    void getAllRecipes() {
        Recipe recipe1 = new Recipe();
        recipe1.setRecipeId(1);
        recipe1.setRecipeName("Spaghetti Bolognese");
        recipe1.setRecipeShortDescription("Classic Italian meat sauce");
        recipe1.setPrepTime(20);
        recipe1.setCookingTime(60);
        recipe1.setRecipeSteps("1. Brown ground beef...");
        recipe1.setUser(new User());

        Recipe recipe2 = new Recipe();
        recipe2.setRecipeId(2);
        recipe2.setRecipeName("Chicken Curry");
        recipe2.setRecipeShortDescription("Aromatic and flavorful curry");
        recipe2.setPrepTime(30);
        recipe2.setCookingTime(45);
        recipe2.setRecipeSteps("1. Marinate chicken...");
        recipe2.setUser(new User());

        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        when(entityManager.createQuery(anyString(), eq(Recipe.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(recipes);

        List<Recipe> result = recipeService.getAllRecipes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(recipes));
    }
}