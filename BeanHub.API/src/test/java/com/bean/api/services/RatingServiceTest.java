package com.bean.api.services;

import com.bean.api.entities.Rating;
import com.bean.api.entities.Recipe;
import com.bean.api.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Rating> query;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRating() {
        Rating rating = new Rating();
        rating.setUser(new User());
        rating.setRecipe(new Recipe());
        rating.setRatingValue(4);
        rating.setRatingDateTime(LocalDateTime.now());

        ratingService.saveRating(rating);

        verify(entityManager, times(1)).persist(rating);
    }

    @Test
    void updateRating() {
        Rating rating = new Rating();
        rating.setRatingId(1);
        rating.setUser(new User());
        rating.setRecipe(new Recipe());
        rating.setRatingValue(3);
        rating.setRatingDateTime(LocalDateTime.now());

        ratingService.updateRating(rating);

        verify(entityManager, times(1)).merge(rating);
    }

    @Test
    void deleteRating() {
        Rating rating = new Rating();
        rating.setRatingId(1);
        rating.setUser(new User());
        rating.setRecipe(new Recipe());
        rating.setRatingValue(5);
        rating.setRatingDateTime(LocalDateTime.now());

        when(entityManager.contains(rating)).thenReturn(true);

        ratingService.deleteRating(rating);

        verify(entityManager, times(1)).remove(rating);
    }

    @Test
    void getRatingById() {
        Integer ratingId = 1;
        Rating rating = new Rating();
        rating.setRatingId(ratingId);
        rating.setUser(new User());
        rating.setRecipe(new Recipe());
        rating.setRatingValue(4);
        rating.setRatingDateTime(LocalDateTime.now());

        when(entityManager.find(Rating.class, ratingId)).thenReturn(rating);

        Rating result = ratingService.getRatingById(ratingId);

        assertNotNull(result);
        assertEquals(rating.getRatingId(), result.getRatingId());
        assertNotNull(result.getUser());
        assertNotNull(result.getRecipe());
        assertEquals(rating.getRatingValue(), result.getRatingValue());
        assertEquals(rating.getRatingDateTime(), result.getRatingDateTime());
    }

    @Test
    void getAllRatings() {
        Rating rating1 = new Rating();
        rating1.setRatingId(1);
        rating1.setUser(new User());
        rating1.setRecipe(new Recipe());
        rating1.setRatingValue(5);
        rating1.setRatingDateTime(LocalDateTime.now());

        Rating rating2 = new Rating();
        rating2.setRatingId(2);
        rating2.setUser(new User());
        rating2.setRecipe(new Recipe());
        rating2.setRatingValue(4);
        rating2.setRatingDateTime(LocalDateTime.now());

        List<Rating> ratings = Arrays.asList(rating1, rating2);

        when(entityManager.createQuery(anyString(), eq(Rating.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(ratings);

        List<Rating> result = ratingService.getAllRatings();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(ratings));
    }
}