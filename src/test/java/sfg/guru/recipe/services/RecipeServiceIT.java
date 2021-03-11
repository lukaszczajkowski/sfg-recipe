package sfg.guru.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.converters.RecipeCommandToRecipe;
import sfg.guru.recipe.converters.RecipeToRecipeCommand;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.repositories.RecipeRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;

    private static final String NEW_DESCRIPTION = "New Description";

    @Test
    @Transactional
    public void shouldSaveDescription() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
    }
}
