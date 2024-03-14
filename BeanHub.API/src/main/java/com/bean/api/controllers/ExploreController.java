package com.bean.api.controllers;

import java.util.List;

import com.bean.api.entities.Recipe;
import com.bean.api.services.RatingService;
import com.bean.api.services.RecipeService;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.TypedQuery;

import org.hibernate.mapping.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/explore")
public class ExploreController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/getallrecipes/{filterValue}")
    public List<Recipe> getAllRecipes(@PathVariable("filterValue") String filterValue) {
        List<Recipe> recipes = recipeService.getSortedExplore(filterValue);
        return recipes;
    }

    @GetMapping("/getaveragerating/{recipeId}")
    public Double getAverageRating(@PathVariable("recipeId") int recipeId) {
        Double averageRating = ratingService.getAverageRatingByRecipeId(recipeId);
        return averageRating;
    }

    // @GetMapping("/get/{id}")
    // public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") Long id) {
    //     Recipe recipe = recipeService.getRecipeById(id);
    //     if(recipe != null)
    //         return new ResponseEntity<>(recipe, HttpStatus.OK);
    //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
}