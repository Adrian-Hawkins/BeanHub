package com.bean.api.controllers;

import com.bean.api.enums.RatingEnums;
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
        RatingEnums.SortType sortType;
        switch (filterValue) {
            case "1":
                sortType = RatingEnums.SortType.NEWEST;
                break;
            case "2":
                sortType = RatingEnums.SortType.OLDEST;
                break;
            case "3":
                sortType = RatingEnums.SortType.HIGHESTRATED;
                break;
            case "4":
                sortType = RatingEnums.SortType.LOWESTRATED;
                break;
            default:
                sortType = RatingEnums.SortType.NEWEST;
                break;
        }
        List<Recipe> recipes = ratingService.getSortedFeed(userId, sortType);

        if (recipes.isEmpty()) {
            return null;
        }
        return recipes;
    }
}