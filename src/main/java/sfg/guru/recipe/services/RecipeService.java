package sfg.guru.recipe.services;

import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.domain.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe save(Recipe recipe);

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long idToDelete);
}
