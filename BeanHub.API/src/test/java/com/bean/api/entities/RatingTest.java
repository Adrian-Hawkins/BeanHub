package com.bean.api.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingTest {

    private Rating rating;

    @BeforeEach
    public void setUp() {
        rating = new Rating();
    }

    @Test
    public void testRatingId() {
        rating.setRatingId(1);
        assertEquals(1, rating.getRatingId());
    }

    @Test
    public void testUser() {
        User user = new User();
        rating.setUser(user);
        assertEquals(user, rating.getUser());
    }

    @Test
    public void testRecipe() {
        Recipe recipe = new Recipe();
        rating.setRecipe(recipe);
        assertEquals(recipe, rating.getRecipe());
    }

    @Test
    public void testRatingValue() {
        rating.setRatingValue(5);
        assertEquals(5, rating.getRatingValue());
    }

    @Test
    public void testRatingDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        rating.setRatingDateTime(dateTime);
        assertEquals(dateTime, rating.getRatingDateTime());
    }
}
