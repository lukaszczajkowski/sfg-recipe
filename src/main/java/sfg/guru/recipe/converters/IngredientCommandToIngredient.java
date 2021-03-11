package sfg.guru.recipe.converters;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import sfg.guru.recipe.commands.IngredientCommand;
import sfg.guru.recipe.domain.Ingredient;
import sfg.guru.recipe.domain.Recipe;

@Slf4j
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        log.info("Converting IngredientCommand " + source);
        if (source == null) {
            return null;
        }
        log.info("Converting IngredientCommand " + source);
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        log.info("Converting IngredientCommand " + source);
        if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        log.info("Converting IngredientCommand " + source);
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(unitOfMeasureConverter.convert(source.getUom()));
        log.info("Converting returning " + ingredient);
        return ingredient;
    }
}
