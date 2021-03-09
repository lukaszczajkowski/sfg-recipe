package sfg.guru.recipe.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @InjectMocks
    IndexController indexController;

    String expected = "index";

    @BeforeEach
    void setUp() {
        openMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(indexController)
                .build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndex() {
        //given
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        when(recipeService.getAllRecipes())
                .thenReturn(recipes);

        ArgumentCaptor<List<Recipe>> argumentCaptor =
                ArgumentCaptor.forClass(List.class);

        //when
        String actual = indexController.getIndex(model);

        //then
        assertThat(actual, equalTo(expected));
        verify(recipeService, times(1))
                .getAllRecipes();
        verify(model, times(1))
                .addAttribute(eq("recipes"), argumentCaptor.capture());

        List<Recipe> listInController = argumentCaptor.getValue();

        assertThat(listInController, hasSize(2));
        assertThat(listInController, equalTo(recipes));
    }
}