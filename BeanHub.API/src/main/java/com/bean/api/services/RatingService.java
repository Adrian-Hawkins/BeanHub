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
    public Rating getRatingById(Integer ratingId) {
        return entityManager.find(Rating.class, ratingId);
    }

    public List<Recipe> getSortedFeed(@RequestParam(value = "userId") int userId,
            @RequestParam(value = "sort", defaultValue = "newest") String sort) {

        String jpql = "SELECT r.recipe.recipeId FROM Rating r WHERE r.user.userId = :userId";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        query.setParameter("userId", userId);
        List<Integer> recipeIds = query.getResultList();

        Map<Integer, Double> averageRatings = getAverageRatings(recipeIds);

        switch (sort) {
            case "newest":
                return getSortedRecipesByDateAdded(recipeIds, Comparator.nullsLast(Comparator.reverseOrder()));
            case "oldest":
                return getSortedRecipesByDateAdded(recipeIds, Comparator.nullsLast(Comparator.naturalOrder()));
            case "highest rated":
                return getSortedRecipesByAverageRating(recipeIds, averageRatings, sort);
            case "lowest rated":
                return getSortedRecipesByAverageRating(recipeIds, averageRatings, sort);
            default:
                return getSortedRecipesByDateAdded(recipeIds, Comparator.nullsLast(Comparator.reverseOrder()));
        }
    }

    private Map<Integer, Double> getAverageRatings(List<Integer> recipeIds) {
        String ratingJpql = "SELECT AVG(r.ratingValue) FROM Rating r WHERE r.recipe.recipeId = :recipeId";
        Map<Integer, Double> averageRatings = new HashMap<>();
        for (Integer recipeId : recipeIds) {
            TypedQuery<Double> avgQuery = entityManager.createQuery(ratingJpql, Double.class);
            avgQuery.setParameter("recipeId", recipeId);
            Double avgRating = avgQuery.getSingleResult();
            averageRatings.put(recipeId, avgRating != null ? avgRating : 0.0);
        }
        return averageRatings;
    }

    private List<Recipe> getSortedRecipesByDateAdded(List<Integer> recipeIds, Comparator<Long> comparator) {
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

    private List<Recipe> getSortedRecipesByAverageRating(List<Integer> recipeIds, Map<Integer, Double> averageRatings,
            String sort) {
        switch (sort) {
            case "lowest rated":
                recipeIds.sort(Comparator.comparingDouble(averageRatings::get));
                break;
            case "highest rated":
                recipeIds.sort(Comparator.comparingDouble(averageRatings::get).reversed());
                break;
            default:
                break;
        }
        System.out.println(recipeIds);
        String recipeJpql = "SELECT r FROM Recipe r WHERE r.recipeId IN :recipeIds";
        TypedQuery<Recipe> recipeQuery = entityManager.createQuery(recipeJpql, Recipe.class);
        recipeQuery.setParameter("recipeIds", recipeIds);
        List<Recipe> recipes = recipeQuery.getResultList();
        return recipes;
    }

    @Transactional(readOnly = true)
    public List<Rating> getAllRatings() {
        String jpql = "SELECT r FROM Rating r";
        TypedQuery<Rating> query = entityManager.createQuery(jpql, Rating.class);
        return query.getResultList();
    }
}
