package com.bean.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.api.entities.Recipe;
import com.bean.api.services.UserService;
import com.bean.api.services.ViewPastRecipesService;

@RestController
@RequestMapping("/api/viewpastrecipes")
public class ViewPastRecipesController {
    @Autowired
        private UserService userService;
    
    @Autowired
        private ViewPastRecipesService pastRecipeService;

    @GetMapping("/getUserRecipes")
    public List<Recipe> GetUserRecipes(@RequestParam("username") String username) {
        int userId = userService.getUserByUsername(username).getUserId();
        List<Recipe> recipes = pastRecipeService.getUserRecipes(userId);
        return recipes;
    }
}
