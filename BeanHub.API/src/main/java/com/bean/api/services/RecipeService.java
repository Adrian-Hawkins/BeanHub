package com.bean.api.services;

import com.bean.api.entities.Rating;
import com.bean.api.entities.Recipe;
import com.bean.api.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class RecipeService {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveRecipe(Recipe recipe) {
        entityManager.persist(recipe);
    }

    @Transactional
    public void updateRecipe(Recipe recipe) {
        entityManager.merge(recipe);
    }

    @Transactional
    public void deleteRecipe(Recipe recipe) {
        entityManager.remove(entityManager.contains(recipe) ? recipe : entityManager.merge(recipe));
    }

    @Transactional(readOnly = false)
    public Recipe getRecipeById(Long recipeId) {
        return entityManager.find(Recipe.class, recipeId);
    }

    @Transactional(readOnly = true)
    public List<Recipe> getFilteredRecipesByUsername(String username) {
        String jpql = "SELECT r FROM Recipe r JOIN r.user u WHERE u.username = :username ORDER BY u.username";
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    // @Kay
    // Switch -> enum
    // requestparam-why?
    public List<Recipe> getSortedExplore(@RequestParam(value = "sort", defaultValue = "1") String sort) {
        String jpql = "SELECT r.recipeId FROM Recipe r";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        List<Long> recipeIds = query.getResultList();

        Map<Long, Double> averageRatings = getAverageRatings(recipeIds);

        switch (sort) {
            case "1": // newest
                return getSortedRecipesByDateAdded(recipeIds,
                        Comparator.nullsLast(Comparator.reverseOrder()));
            case "2": // oldest
                return getSortedRecipesByDateAdded(recipeIds,
                        Comparator.nullsLast(Comparator.naturalOrder()));
            case "3": // highest rated
                return getSortedRecipesByAverageRating(recipeIds, averageRatings, sort);
            case "4": // lowest rated
                return getSortedRecipesByAverageRating(recipeIds, averageRatings, sort);
            default:
                return getSortedRecipesByDateAdded(recipeIds,
                        Comparator.nullsLast(Comparator.reverseOrder()));
        }
    }

    // @Kay
    // What is the return type of the query, Object is not good enough. I want to know what to expect.
    // What is the type of result?
    public Map<Long, Double> getAverageRatings(List<Long> recipeIds) {
        String ratingJpql = "SELECT r.recipe.recipeId, AVG(r.ratingValue) FROM Rating r WHERE r.recipe.recipeId IN :recipeIds GROUP BY r.recipe.recipeId";
        TypedQuery<Object[]> avgQuery = entityManager.createQuery(ratingJpql, Object[].class);
        avgQuery.setParameter("recipeIds", recipeIds);
        List<Object[]> results = avgQuery.getResultList();
        Map<Long, Double> averageRatings = new HashMap<>();
        for (Object[] result : results) {
            Long recipeId = (Long) result[0];
            Double avgRating = (Double) result[1];
            averageRatings.put(recipeId, avgRating != null ? avgRating : 0.0);
        }
        return averageRatings;
    }

    // @Kay and @Moshe - I (David) have fixed this function to make it okay.
    // I am still not happy with what's happening at the return.
    // Moshe has a similar function to fix, so this one belongs to Kay.
    private List<Recipe> getSortedRecipesByDateAdded(List<Long> recipeIds, Comparator<Long> comparator) {
        // JPQL query that returns all recipes with the recipe IDs in the provided list.
        String recipeJpql = "SELECT r FROM Recipe r WHERE r.recipeId IN :recipeIds";
        TypedQuery<Recipe> recipeQuery = entityManager.createQuery(recipeJpql, Recipe.class);
        // Adds a parameter to the query. Sets the parameter to the list passed in to the function.
        recipeQuery.setParameter("recipeIds", recipeIds);
        // Run the query 
        List<Recipe> recipes = recipeQuery.getResultList();
        // Sorts the results by their dates.
        // Ascending or descending is chosen based on the comparator parameter passed in.
        recipes.sort((recipe1, recipe2) -> { // r1 is now recipe1 and r2 is now recipe2...
            // date1 is the date that recipe1 was added, same for 2.
            LocalDateTime date1 = recipe1.getDateAdded();
            LocalDateTime date2 = recipe2.getDateAdded();
            // IDK if setting it to null if it is null here is a good idea. Maybe give it a default value earlier instead.
            return comparator.compare(date1 != null ? date1.toEpochSecond(ZoneOffset.UTC) : null,
                    date2 != null ? date2.toEpochSecond(ZoneOffset.UTC) : null);
        });
        return recipes;
    }
    
    // @Kay - switch->enum
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

    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        String jpql = "SELECT r FROM Recipe r";
        TypedQuery<Recipe> query = entityManager.createQuery(jpql, Recipe.class);
        return query.getResultList();
    }

    
    @Transactional(readOnly = false)
    public String rateRecipe(Long recipeID, Long rating, Long userID) {
        // Get if the recipe was rated
        String jpql1 = "SELECT r FROM Rating r WHERE r.user.id = :userID AND r.recipe.id = :recipeID";
        TypedQuery<Rating> query = entityManager.createQuery(jpql1, Rating.class);
        
        query.setParameter("userID", userID);
        query.setParameter("recipeID", recipeID);

        var queryOut = query.getResultList();
        int rating_id = queryOut.size();

        if (rating_id > 0){
            //Update the table if rated by user already
            String jpql2 = "UPDATE Rating r SET r.ratingValue = :newRating WHERE r.user.id = :userID AND r.recipe.id = :recipeID";
            Query query2 = entityManager.createQuery(jpql2);

            query2.setParameter("newRating", rating);
            query2.setParameter("userID", userID);
            query2.setParameter("recipeID", recipeID);
            query2.executeUpdate();

            return "Updated";
        } else {
            //Else insert to the table:
            Recipe ratingRecipe = getRecipeById(recipeID);
            User ratingUser = userService.getUserById(userID.intValue());
            Rating newRating = new Rating();
            newRating.setUser(ratingUser);
            newRating.setRecipe(ratingRecipe);
            newRating.setRatingValue(rating.intValue());
            
            entityManager.persist(newRating);

            return "Added";
        }
    }
}
