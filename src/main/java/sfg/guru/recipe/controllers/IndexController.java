package sfg.guru.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sfg.guru.recipe.services.RecipeService;

@Controller
@Slf4j
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndex(Model model) {
        log.debug("Getting index page....");

        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "index";
    }
}
