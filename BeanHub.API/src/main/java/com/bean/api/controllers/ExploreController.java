package com.bean.api.controllers;

import java.util.List;
import java.util.Map;
import com.bean.api.entities.Recipe;
import com.bean.api.services.RatingService;
import com.bean.api.services.RecipeService;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;

import jakarta.persistence.TypedQuery;

// import org.hibernate.mapping.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/explore")
public class ExploreController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/getallrecipes/{filterValue}")
    public List<Recipe> getAllRecipes(@PathVariable("filterValue") String filterValue) {
        List<Recipe> recipes = recipeService.getSortedExplore(filterValue);
        return recipes;
    }

    @GetMapping("/getaverageratings/{recipeIds}")
    public Map<Long, Double> getAverageRating(@PathVariable("recipeIds") String recipeIds) {
        // Convert the comma-separated string of recipe IDs into a list of Long values
        List<Long> recipeIdsList = Arrays.stream(recipeIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        Map<Long, Double> averageRatings = recipeService.getAverageRatings(recipeIdsList);
        return averageRatings;
    }
}