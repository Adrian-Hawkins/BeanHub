package com.bean.api.controllers;

import com.bean.api.entities.*;
import com.bean.api.requests.postRecipeRequest;
import com.bean.api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private RecipeIngredientsService recipeIngredientsService;
    @PostMapping("/post")
    public Map<Object, Object> postRecipe(@RequestBody postRecipeRequest req) {
        Map<Object, Object> response = new HashMap<Object, Object>();
        try {
            User user = userService.getUserByUsername(req.getUser().getUsername());
            Recipe recipe = req.getRecipe();
            recipe.setUser(user);
            recipeService.saveRecipe(recipe);
            List<RecipeIngredients> recipeIngredients = req.getRecipeIngredients();

            recipeIngredients.forEach(recipeIngredient -> {
                Ingredient ingredient = recipeIngredient.getIngredient();
                Unit unit = recipeIngredient.getUnit();

                Ingredient tempIngredient = ingredientService.getIngredientByName(ingredient.getIngredientName());
                if(tempIngredient == null)
                    ingredientService.saveIngredient(ingredient);
                else
                    ingredient = tempIngredient;

                Unit tempUnit = unitService.getUnitByName(unit.getUnitName());
                if(tempUnit == null)
                    unitService.saveUnit(unit);
                else
                    unit = tempUnit;

                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setUnit(unit);
                recipeIngredient.setRecipe(recipe);
                recipeIngredientsService.saveRecipeIngredient(recipeIngredient);
            });
            response.put("status", "Success");
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.put("status", "Failure");
            return response;
        }
    }

    // WIP
    @GetMapping("/get/{id}")
    public ResponseEntity<RecipeIngredients> getRecipeById(@PathVariable("id") Long id) {
        RecipeIngredients recipeIngredients = recipeIngredientsService.getRecipeIngredientById(id);
        if(recipeIngredients != null)
            return new ResponseEntity<>(recipeIngredients, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
