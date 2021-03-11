package sfg.guru.recipe.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sfg.guru.recipe.commands.IngredientCommand;
import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.services.IngredientService;
import sfg.guru.recipe.services.RecipeService;
import sfg.guru.recipe.services.UnitOfMeasureService;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        ingredientController = new IngredientController(recipeService,
                ingredientService,
                unitOfMeasureService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(ingredientController)
                .build();
    }

    @Test
    public void shouldListIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong()))
                .thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    public void shouldShowIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findByRecipeAndIngredientId(anyLong(), anyLong()))
                .thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));

        verify(ingredientService).findByRecipeAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void shouldSaveOrUpdate() throws Exception {

        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        when(ingredientService.saveIngredientCommand(any(IngredientCommand.class)))
                .thenReturn(command);

        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

    }
}