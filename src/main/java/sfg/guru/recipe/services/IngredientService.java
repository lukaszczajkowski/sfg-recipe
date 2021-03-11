package sfg.guru.recipe.services;

import sfg.guru.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
