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

        ingredientService.saveIngredient(ingredient);

        verify(entityManager, times(1)).persist(ingredient);
    }

    @Test
    void updateIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setIngredientName("Sugar");

        ingredientService.updateIngredient(ingredient);

        verify(entityManager, times(1)).merge(ingredient);
    }

    @Test
    void deleteIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(1);
        ingredient.setIngredientName("Flour");

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

        when(entityManager.find(Ingredient.class, ingredientId)).thenReturn(ingredient);

        Ingredient result = ingredientService.getIngredientById(ingredientId);

        assertNotNull(result);
        assertEquals(ingredient.getIngredientId(), result.getIngredientId());
        assertEquals(ingredient.getIngredientName(), result.getIngredientName());
    }

    @Test
    void getAllIngredients() {
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setIngredientId(1);
        ingredient1.setIngredientName("Cinnamon");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setIngredientId(2);
        ingredient2.setIngredientName("Vanilla");

        List<Ingredient> ingredients = Arrays.asList(ingredient1, ingredient2);

        when(entityManager.createQuery(anyString(), eq(Ingredient.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(ingredients);

        List<Ingredient> result = ingredientService.getAllIngredients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(ingredients));
    }
}