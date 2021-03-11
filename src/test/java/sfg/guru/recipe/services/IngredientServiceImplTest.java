package sfg.guru.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import sfg.guru.recipe.commands.IngredientCommand;
import sfg.guru.recipe.converters.IngredientToIngredientCommand;
import sfg.guru.recipe.domain.Ingredient;
import sfg.guru.recipe.domain.Recipe;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class IngredientServiceImplTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientServiceImpl ingredientService;

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 1L;
    private Recipe recipe;
    private Ingredient ingredient;

    @Before
    public void setUp() {
        openMocks(this);

        ingredientService = new IngredientServiceImpl(recipeService, ingredientToIngredientCommand);

        recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
    }

    @Test
    public void shouldFindIngredientByRecipeIdAndIngredientId() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);

        when(recipeService.findById(anyLong()))
                .thenReturn(recipe);
        when(ingredientToIngredientCommand.convert(any()))
                .thenReturn(ingredientCommand);

        IngredientCommand actual =
                ingredientService.findByRecipeAndIngredientId(RECIPE_ID, INGREDIENT_ID);

        assertEquals(INGREDIENT_ID, actual.getId());
    }
}