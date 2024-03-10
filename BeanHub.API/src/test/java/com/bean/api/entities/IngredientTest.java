package com.bean.api.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient();
    }

    @Test
    void testGetSetIngredientId() {
        Integer expectedId = 1;
        ingredient.setIngredientId(expectedId);
        Integer actualId = ingredient.getIngredientId();
        assertEquals(expectedId, actualId);
    }

    @Test
    void testGetSetIngredientName() {
        String expectedName = "Salt";
        ingredient.setIngredientName(expectedName);
        String actualName = ingredient.getIngredientName();
        assertEquals(expectedName, actualName);
    }
}