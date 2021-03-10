package sfg.guru.recipe.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.repositories.RecipeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;

class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    private RecipeServiceImpl recipeService;

    Recipe recipe;
    List<Recipe> inputDataSet;
    List<Recipe> retrievedDataSet;

    @BeforeEach
    void setUp() {
        openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
        recipe = new Recipe();
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
}