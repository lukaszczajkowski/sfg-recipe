package sfg.guru.recipe.services;

import org.springframework.stereotype.Service;
import sfg.guru.recipe.commands.IngredientCommand;
import sfg.guru.recipe.converters.IngredientToIngredientCommand;
import sfg.guru.recipe.domain.Ingredient;
import sfg.guru.recipe.domain.Recipe;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeService recipeService,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeService = recipeService;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeAndIngredientId(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeService.findById(recipeId);

        Ingredient ingredient = recipe.getIngredients()
                .stream()
                .filter(ingredient1 -> ingredient1.getId() == ingredientId)
                .findFirst()
                .orElse(null);

        return ingredientToIngredientCommand.convert(ingredient);
    }
}
