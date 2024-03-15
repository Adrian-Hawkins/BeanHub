package com.bean.api.services;

import com.bean.api.entities.Rating;
import com.bean.api.entities.Recipe;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

@Service
public class RatingService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Transactional
    public void updateRating(Rating rating) {
        entityManager.merge(rating);
    }

    @Transactional
    public void deleteRating(Rating rating) {
        entityManager.remove(entityManager.contains(rating) ? rating : entityManager.merge(rating));
    }

    @Transactional(readOnly = false)
    public Rating getRatingById(Long ratingId) {
        return entityManager.find(Rating.class, ratingId);
    }

    // @Moshe
    // Replace switch with enum
    public List<Recipe> getSortedFeed(int userId, String sort) {

        String jpql = "SELECT r.recipe.recipeId FROM Rating r WHERE r.user.userId = :userId";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("userId", userId);
        List<Long> recipeIds = query.getResultList();

        Map<Long, Double> averageRatings = getAverageRatings(recipeIds);

        switch (sort) {
            case "1":
                return getSortedRecipesByDateAdded(recipeIds, Comparator.nullsLast(Comparator.reverseOrder()));
            case "2":
                return getSortedRecipesByDateAdded(recipeIds, Comparator.nullsLast(Comparator.naturalOrder()));
            case "3":
                return getSortedRecipesByAverageRating(recipeIds, averageRatings, sort);
            case "4":
                return getSortedRecipesByAverageRating(recipeIds, averageRatings, sort);
            default:
                return getSortedRecipesByDateAdded(recipeIds, Comparator.nullsLast(Comparator.reverseOrder()));
        }
    }

    private Map<Long, Double> getAverageRatings(List<Long> recipeIds) {
        String ratingJpql = "SELECT AVG(r.ratingValue) FROM Rating r WHERE r.recipe.recipeId = :recipeId";
        Map<Long, Double> averageRatings = new HashMap<>();
        for (Long recipeId : recipeIds) {
            TypedQuery<Double> avgQuery = entityManager.createQuery(ratingJpql, Double.class);
            avgQuery.setParameter("recipeId", recipeId);
            Double avgRating = avgQuery.getSingleResult();
            averageRatings.put(recipeId, avgRating != null ? avgRating : 0.0);
        }
        return averageRatings;
    }

    // @Moshe
    // r1/r2
    // whats that return statement doing exactly?
    private List<Recipe> getSortedRecipesByDateAdded(List<Long> recipeIds, Comparator<Long> comparator) {
        String recipeJpql = "SELECT r FROM Recipe r WHERE r.recipeId IN :recipeIds";
        TypedQuery<Recipe> recipeQuery = entityManager.createQuery(recipeJpql, Recipe.class);
        recipeQuery.setParameter("recipeIds", recipeIds);
        List<Recipe> recipes = recipeQuery.getResultList();
        recipes.sort((r1, r2) -> {
            LocalDateTime date1 = r1.getDateAdded();
            LocalDateTime date2 = r2.getDateAdded();
            return comparator.compare(date1 != null ? date1.toEpochSecond(ZoneOffset.UTC) : null,
                    date2 != null ? date2.toEpochSecond(ZoneOffset.UTC) : null);
        });
        return recipes;
    }


    // @Moshe
    // Switch -> enum
    private List<Recipe> getSortedRecipesByAverageRating(List<Long> recipeIds, Map<Long, Double> averageRatings,
            String sort) {
        switch (sort) {
            case "4":
                recipeIds.sort(Comparator.comparingDouble(averageRatings::get));
                break;
            case "3":
                recipeIds.sort(Comparator.comparingDouble(averageRatings::get).reversed());
                break;
            default:
                break;
        }
        // Fetch all recipes from the database
        List<Recipe> allRecipes = entityManager.createQuery("SELECT r FROM Recipe r", Recipe.class).getResultList();
        Map<Long, Recipe> recipeMap = allRecipes.stream()
                .collect(Collectors.toMap(Recipe::getRecipeId, Function.identity()));
        List<Recipe> sortedRecipes = new ArrayList<>();
        for (Long recipeId : recipeIds) {
            Recipe recipe = recipeMap.get(recipeId);
            if (recipe != null) {
                sortedRecipes.add(recipe);
            }
        }
        return sortedRecipes;
    }

    public Double getAverageRatingByRecipeId(int recipeId) {
        String ratingJpql = "SELECT AVG(r.ratingValue) FROM Rating r WHERE r.recipe.recipeId = :recipeId";
        TypedQuery<Double> avgQuery = entityManager.createQuery(ratingJpql, Double.class);
        avgQuery.setParameter("recipeId", recipeId);
        Double avgRating = avgQuery.getSingleResult();
        if (avgRating != null) {
            avgRating = Math.round(avgRating * 10.0) / 10.0;
        } else {
            avgRating = 0.0;
        }
        return avgRating;
    }

    @Transactional(readOnly = true)
    public List<Rating> getAllRatings() {
        String jpql = "SELECT r FROM Rating r";
        TypedQuery<Rating> query = entityManager.createQuery(jpql, Rating.class);
        return query.getResultList();
    }
}
