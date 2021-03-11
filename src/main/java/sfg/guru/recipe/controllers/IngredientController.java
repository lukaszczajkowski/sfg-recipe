package sfg.guru.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sfg.guru.recipe.commands.IngredientCommand;
import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.commands.UnitOfMeasureCommand;
import sfg.guru.recipe.services.IngredientService;
import sfg.guru.recipe.services.RecipeService;
import sfg.guru.recipe.services.UnitOfMeasureService;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId,
                                         @PathVariable Long ingredientId,
                                         Model model) {
        model.addAttribute("ingredient", ingredientService
                .findByRecipeAndIngredientId(recipeId, ingredientId));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable Long recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        //todo raise exception if null

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);

        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

}
