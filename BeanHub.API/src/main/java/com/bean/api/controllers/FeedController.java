package com.bean.api.controllers;

import java.util.List;

// import com.bean.api.entities.Rating;
import com.bean.api.entities.Recipe;
// import com.bean.api.entities.RecipeIngredients;
import com.bean.api.entities.User;
import com.bean.api.services.RatingService;
// import com.bean.api.services.RecipeIngredientsService;
// import com.bean.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FeedController {
    @Autowired
    private RatingService ratingService;

    // @Autowired
    // private UserService userService;

    @GetMapping("/feed")
    public List<Recipe> getAllRecipeIngredients() {
        // int u = userService.getUserByUsername("test-user").getUserId();
        List<Recipe> recipes = ratingService.getSortedFeed(1, "highest rated");

        return recipes;
    }
}