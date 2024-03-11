package com.bean.api.controllers;

import com.bean.api.entities.*;
import com.bean.api.requests.postRecipeRequest;
import com.bean.api.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/Tester")
    public Map<Object, Object> test() {
        Map<Object, Object> response = new HashMap<Object, Object>();

        response.put("recipeIngredients", recipeIngredientsService.getAllRecipeIngredients());
        return  response;
    }
}
