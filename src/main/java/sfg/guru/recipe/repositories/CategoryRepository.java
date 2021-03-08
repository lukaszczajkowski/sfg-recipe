package sfg.guru.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import sfg.guru.recipe.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
