package com.bean.api.controllers;

import java.util.List;

// import com.bean.api.entities.Rating;
import com.bean.api.entities.Recipe;
// import com.bean.api.entities.RecipeIngredients;
import com.bean.api.entities.User;
import com.bean.api.services.RatingService;
// import com.bean.api.services.RecipeIngredientsService;
// import com.bean.api.services.UserService;
import com.bean.api.services.UserService;

import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
public class FeedController {
    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/getallrecipes/{filterValue}")
    public List<Recipe> getAllRecipeIngredients(@PathVariable("filterValue") String filterValue,
            @RequestParam("username") String username) {
        int userId = userService.getUserByUsername(username).getUserId();
        List<Recipe> recipes = ratingService.getSortedFeed(userId, filterValue);

        if (recipes.isEmpty()) {
            return null;
        }
        return recipes;
    }
}