package com.bean.api.services;

import com.bean.api.entities.Ingredient;
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

class IngredientServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Ingredient> query;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName("Salt");
        ingredient.setIngredientShortDescription("Mineral compound");

        ingredientService.saveIngredient(ingredient);

        verify(entityManager, times(1)).persist(ingredient);
    }

    @Test
    void updateIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setIngredientName("Sugar");
        ingredient.setIngredientShortDescription("Sweet carbohydrate");

        ingredientService.updateIngredient(ingredient);

        verify(entityManager, times(1)).merge(ingredient);
    }

    @Test
    void deleteIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setIngredientName("Flour");
        ingredient.setIngredientShortDescription("Powder from grains");

        when(entityManager.contains(ingredient)).thenReturn(true);

        ingredientService.deleteIngredient(ingredient);

        verify(entityManager, times(1)).remove(ingredient);
    }

    @Test
    void getIngredientById() {
        Integer ingredientId = 1;
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(ingredientId);
        ingredient.setIngredientName("Pepper");
        ingredient.setIngredientShortDescription("Spice made from berries");

        when(entityManager.find(Ingredient.class, ingredientId)).thenReturn(ingredient);

        Ingredient result = ingredientService.getIngredientById(ingredientId);

        assertNotNull(result);
        assertEquals(ingredient.getIngredientId(), result.getIngredientId());
        assertEquals(ingredient.getIngredientName(), result.getIngredientName());
        assertEquals(ingredient.getIngredientShortDescription(), result.getIngredientShortDescription());
    }

    @Test
    void getAllIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setIngredientId(1);
        ingredient1.setIngredientName("Cinnamon");
        ingredient1.setIngredientShortDescription("Spice from tree bark");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setIngredientId(2);
        ingredient2.setIngredientName("Vanilla");
        ingredient2.setIngredientShortDescription("Flavoring from bean pods");

        List<Ingredient> ingredients = Arrays.asList(ingredient1, ingredient2);

        when(entityManager.createQuery(anyString(), eq(Ingredient.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(ingredients);

        List<Ingredient> result = ingredientService.getAllIngredients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(ingredients));
    }
}