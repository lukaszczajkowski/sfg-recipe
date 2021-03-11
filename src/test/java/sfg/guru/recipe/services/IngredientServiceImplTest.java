package sfg.guru.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import sfg.guru.recipe.commands.IngredientCommand;
import sfg.guru.recipe.converters.IngredientCommandToIngredient;
import sfg.guru.recipe.converters.IngredientToIngredientCommand;
import sfg.guru.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import sfg.guru.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import sfg.guru.recipe.domain.Ingredient;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.repositories.RecipeRepository;
import sfg.guru.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class IngredientServiceImplTest {

    @Mock
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientServiceImpl ingredientService;

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 1L;
    private Recipe recipe;
    private Ingredient ingredient;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() {
        openMocks(this);

        ingredientService = new IngredientServiceImpl(recipeService,
                ingredientToIngredientCommand,
                recipeRepository,
                unitOfMeasureRepository,
                ingredientCommandToIngredient);
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeAndIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveRecipeCommand() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
}