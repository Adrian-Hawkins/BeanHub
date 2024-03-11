package com.bean.api.controllers;

import java.util.List;

import com.bean.api.entities.Recipe;
import com.bean.api.services.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExploreController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/explore")
    public List<Recipe> getAllRecipeIngredients() {
        List<Recipe> recipes = recipeService.getSortedExplore("lowest rated");
        return recipes;
    }
}