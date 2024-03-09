package com.bean.api.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeTest {

    @InjectMocks
    private Recipe recipe;

    @Mock
    private User user;

    @Mock
    private Rating rating;

    @BeforeEach
    public void setUp() {
        recipe = new Recipe();
    }

    @Test
    public void testRecipeId() {
        recipe.setRecipeId(1);
        assertEquals(1, recipe.getRecipeId());
    }

    @Test
    public void testRecipeName() {
        recipe.setRecipeName("Test Recipe");
        assertEquals("Test Recipe", recipe.getRecipeName());
    }

    @Test
    public void testRecipeShortDescription() {
        recipe.setRecipeShortDescription("Short Description");
        assertEquals("Short Description", recipe.getRecipeShortDescription());
    }

    @Test
    public void testPrepTime() {
        recipe.setPrepTime(30);
        assertEquals(30, recipe.getPrepTime());
    }

    @Test
    public void testCookingTime() {
        recipe.setCookingTime(45);
        assertEquals(45, recipe.getCookingTime());
    }

    @Test
    public void testRecipeSteps() {
        recipe.setRecipeSteps("Step 1, Step 2, Step 3");
        assertEquals("Step 1, Step 2, Step 3", recipe.getRecipeSteps());
    }

    @Test
    public void testUser() {
        recipe.setUser(user);
        assertEquals(user, recipe.getUser());
    }
}
