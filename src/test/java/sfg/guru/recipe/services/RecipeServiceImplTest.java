package sfg.guru.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.converters.RecipeCommandToRecipe;
import sfg.guru.recipe.converters.RecipeToRecipeCommand;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.exceptions.NotFoundException;
import sfg.guru.recipe.repositories.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    private RecipeServiceImpl recipeService;

    private static final Long ID_TO_DELETE = 3L;
    Recipe recipe;
    Recipe recipeToDelete;
    List<Recipe> inputDataSet;
    List<Recipe> retrievedDataSet;

    @BeforeEach
    void setUp() {
        openMocks(this);
        recipeService =
                new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
        recipe = new Recipe();
        recipeToDelete = new Recipe();
        recipeToDelete.setId(ID_TO_DELETE);
        inputDataSet = List.of(recipe);
    }

    @Test
    void getAllRecipes() {
        when(recipeRepository.findAll())
                .thenReturn(inputDataSet);

        retrievedDataSet = recipeService.getAllRecipes();

        assertThat(retrievedDataSet, hasSize(1));

        assertThat(retrievedDataSet, equalTo(inputDataSet));

        verify(recipeRepository, times(1))
                .findAll();
    }

    @Test
    void shouldGetRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void shouldNotGetRecipeAndThrowException() throws Exception {
        assertThrows(NotFoundException.class, () -> {
            Optional<Recipe> recipeOptional = Optional.empty();

            when(recipeRepository.findById(anyLong()))
                    .thenReturn(recipeOptional);

            Recipe recipeReturned = recipeService.findById(1L);
        });
    }
    @Test
    void shouldSaveRecipeCommand() {
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        Recipe convertedRecipe = new Recipe();
        convertedRecipe.setId(command.getId());

        when(recipeCommandToRecipe.convert(any()))
                .thenReturn(convertedRecipe);
        when(recipeToRecipeCommand.convert(any()))
                .thenReturn(command);
        when(recipeRepository.save(any()))
                .thenReturn(convertedRecipe);

        RecipeCommand actual = recipeService.saveRecipeCommand(command);

        assertThat(actual.getId(), equalTo(command.getId()));
    }

    @Test
    void shouldDeleteById() {
        recipeService.deleteById(ID_TO_DELETE);

        verify(recipeRepository).deleteById(anyLong());
    }
}