package sfg.guru.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.services.RecipeService;

import java.util.List;

@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model) {

        model.addAttribute("recipes",  recipeService.getAllRecipes());

        return "index";
    }
}
