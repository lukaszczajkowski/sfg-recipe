package sfg.guru.recipe.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import sfg.guru.recipe.commands.CategoryCommand;
import sfg.guru.recipe.commands.IngredientCommand;
import sfg.guru.recipe.commands.RecipeCommand;
import sfg.guru.recipe.domain.Category;
import sfg.guru.recipe.domain.Ingredient;
import sfg.guru.recipe.domain.Recipe;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConverter;
    private final NotesCommandToNotes notesConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter,
                                 NotesCommandToNotes notesConverter,
                                 IngredientCommandToIngredient ingredientConverter) {
        this.categoryConverter = categoryConverter;
        this.notesConverter = notesConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(notesConverter.convert(source.getNotes()));
        recipe.setIngredients(convertToIngredientsList(source.getIngredients()));
        recipe.setCategories(convertToCategoriesList(source.getCategories()));
        recipe.setUrl(source.getUrl());

        return recipe;
    }

    private Set<Category> convertToCategoriesList(Set<CategoryCommand> categories) {
        return categories.stream()
                .map(categoryCommand -> categoryConverter.convert(categoryCommand))
                .collect(Collectors.toSet());
    }

    private Set<Ingredient> convertToIngredientsList(Set<IngredientCommand> ingredients) {
        return ingredients.stream()
                .map(ingredientCommand -> ingredientConverter.convert(ingredientCommand))
                .collect(Collectors.toSet());
    }
}
