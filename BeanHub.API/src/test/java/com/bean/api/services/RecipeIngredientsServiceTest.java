package com.bean.api.services;

import com.bean.api.entities.Ingredient;
import com.bean.api.entities.Recipe;
import com.bean.api.entities.RecipeIngredients;
import com.bean.api.entities.Unit;
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

class RecipeIngredientsServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<RecipeIngredients> query;

    @InjectMocks
    private RecipeIngredientsService recipeIngredientsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRecipeIngredient() {
        RecipeIngredients recipeIngredient = new RecipeIngredients();
        recipeIngredient.setIngredient(new Ingredient());
        recipeIngredient.setRecipe(new Recipe());
        recipeIngredient.setQuantity(2);
        recipeIngredient.setUnit(new Unit());

        recipeIngredientsService.saveRecipeIngredient(recipeIngredient);

        verify(entityManager, times(1)).persist(recipeIngredient);
    }

    @Test
    void updateRecipeIngredient() {
        RecipeIngredients recipeIngredient = new RecipeIngredients();
        recipeIngredient.setRecipeIngredientsId(1);
        recipeIngredient.setIngredient(new Ingredient());
        recipeIngredient.setRecipe(new Recipe());
        recipeIngredient.setQuantity(3);
        recipeIngredient.setUnit(new Unit());

        recipeIngredientsService.updateRecipeIngredient(recipeIngredient);

        verify(entityManager, times(1)).merge(recipeIngredient);
    }

    @Test
    void deleteRecipeIngredient() {
        RecipeIngredients recipeIngredient = new RecipeIngredients();
        recipeIngredient.setRecipeIngredientsId(1);
        recipeIngredient.setIngredient(new Ingredient());
        recipeIngredient.setRecipe(new Recipe());
        recipeIngredient.setQuantity(4);
        recipeIngredient.setUnit(new Unit());

        when(entityManager.contains(recipeIngredient)).thenReturn(true);

        recipeIngredientsService.deleteRecipeIngredient(recipeIngredient);

        verify(entityManager, times(1)).remove(recipeIngredient);
    }

    @Test
    void getRecipeIngredientById() {
        Integer recipeIngredientsId = 1;
        RecipeIngredients recipeIngredient = new RecipeIngredients();
        recipeIngredient.setRecipeIngredientsId(recipeIngredientsId);
        recipeIngredient.setIngredient(new Ingredient());
        recipeIngredient.setRecipe(new Recipe());
        recipeIngredient.setQuantity(2);
        recipeIngredient.setUnit(new Unit());

        when(entityManager.find(RecipeIngredients.class, recipeIngredientsId)).thenReturn(recipeIngredient);

        RecipeIngredients result = recipeIngredientsService.getRecipeIngredientById(recipeIngredientsId);

        assertNotNull(result);
        assertEquals(recipeIngredient.getRecipeIngredientsId(), result.getRecipeIngredientsId());
        assertNotNull(result.getIngredient());
        assertNotNull(result.getRecipe());
        assertEquals(recipeIngredient.getQuantity(), result.getQuantity());
        assertNotNull(result.getUnit());
    }

    @Test
    void getAllRecipeIngredients() {
        RecipeIngredients recipeIngredient1 = new RecipeIngredients();
        recipeIngredient1.setRecipeIngredientsId(1);
        recipeIngredient1.setIngredient(new Ingredient());
        recipeIngredient1.setRecipe(new Recipe());
        recipeIngredient1.setQuantity(2);
        recipeIngredient1.setUnit(new Unit());

        RecipeIngredients recipeIngredient2 = new RecipeIngredients();
        recipeIngredient2.setRecipeIngredientsId(2);
        recipeIngredient2.setIngredient(new Ingredient());
        recipeIngredient2.setRecipe(new Recipe());
        recipeIngredient2.setQuantity(3);
        recipeIngredient2.setUnit(new Unit());

        List<RecipeIngredients> recipeIngredients = Arrays.asList(recipeIngredient1, recipeIngredient2);

        when(entityManager.createQuery(anyString(), eq(RecipeIngredients.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(recipeIngredients);

        List<RecipeIngredients> result = recipeIngredientsService.getAllRecipeIngredients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(recipeIngredients));
    }
}