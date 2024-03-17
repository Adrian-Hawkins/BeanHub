package com.bean.api.controllers;

import com.bean.api.enums.SortOption;
import java.util.List;
import com.bean.api.entities.Recipe;
import com.bean.api.services.RatingService;
import com.bean.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        SortOption sortType;
        switch (filterValue) {
            case "1":
                sortType = SortOption.NEWEST;
                break;
            case "2":
                sortType = SortOption.OLDEST;
                break;
            case "3":
                sortType = SortOption.HIGHEST;
                break;
            case "4":
                sortType = SortOption.LOWEST;
                break;
            default:
                sortType = SortOption.NEWEST;
                break;
        }
        List<Recipe> recipes = ratingService.getSortedFeed(userId, sortType);

        if (recipes.isEmpty()) {
            return null;
        }
        return recipes;
    }
}