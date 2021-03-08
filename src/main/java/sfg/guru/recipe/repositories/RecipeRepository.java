package sfg.guru.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import sfg.guru.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
