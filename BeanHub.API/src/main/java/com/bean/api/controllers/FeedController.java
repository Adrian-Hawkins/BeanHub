package com.bean.api.controllers;

import java.util.List;

import com.bean.api.entities.Rating;
import com.bean.api.entities.RecipeIngredients;
import com.bean.api.entities.User;
import com.bean.api.services.RatingService;
import com.bean.api.services.RecipeIngredientsService;
import com.bean.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FeedController {
    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserService userService;

    @GetMapping("/feed")
    public List<Integer> getAllRecipeIngredients() {
        int u = userService.getUserByUsername("test").getUserId();
        List<Integer> recipeIds = ratingService.getRecipeIdsByUserId(u);
        return recipeIds;
        // return u;
    }
}