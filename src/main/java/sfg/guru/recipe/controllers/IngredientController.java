package sfg.guru.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.services.IngredientService;
import sfg.guru.recipe.services.RecipeService;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable Long id, Model model) {
        RecipeCommand command = recipeService.findCommandById(id);

        model.addAttribute("recipe", command);

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredientById(@PathVariable Long recipeId,
                                     @PathVariable Long ingredientId,
                                     Model model) {
        model.addAttribute("ingredient", ingredientService
                .findByRecipeAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

}
