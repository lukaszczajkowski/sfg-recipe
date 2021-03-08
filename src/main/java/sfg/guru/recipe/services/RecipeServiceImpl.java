package sfg.guru.recipe.services;

import org.springframework.stereotype.Service;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.repositories.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeList::add);
        return recipeList;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
