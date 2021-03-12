package sfg.guru.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.converters.RecipeCommandToRecipe;
import sfg.guru.recipe.converters.RecipeToRecipeCommand;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.exceptions.NotFoundException;
import sfg.guru.recipe.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommandConverter;
    private final RecipeCommandToRecipe recipeCommandToRecipeConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeToRecipeCommand recipeToRecipeCommandConverter,
                             RecipeCommandToRecipe recipeCommandToRecipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommandConverter = recipeToRecipeCommandConverter;
        this.recipeCommandToRecipeConverter = recipeCommandToRecipeConverter;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        log.debug("I'm in the service");
        List<Recipe> recipeList = new ArrayList<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeList::add);
        return recipeList;
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);

        if(!optionalRecipe.isPresent()) {
            throw new NotFoundException("Recipe not found");
        }

        return optionalRecipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipeConverter.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeToRecipeCommandConverter.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        return recipe.isPresent() ? recipeToRecipeCommandConverter.convert(recipe.get()) : null;
    }

    @Override
    public void deleteById(Long idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }
}
