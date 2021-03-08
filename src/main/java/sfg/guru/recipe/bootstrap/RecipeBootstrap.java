package sfg.guru.recipe.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sfg.guru.recipe.domain.Difficulty;
import sfg.guru.recipe.domain.Ingredient;
import sfg.guru.recipe.domain.Notes;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.repositories.CategoryRepository;
import sfg.guru.recipe.repositories.RecipeRepository;
import sfg.guru.recipe.repositories.UnitOfMeasureRepository;
import sfg.guru.recipe.services.RecipeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public RecipeBootstrap(UnitOfMeasureRepository unitOfMeasureRepository,
                           CategoryRepository categoryRepository,
                           RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(loadData());
    }

    private List<Recipe> loadData() {

        Recipe grilledChickenTacos = new Recipe();
        grilledChickenTacos.setCookTime(30);
        grilledChickenTacos.setPrepTime(20);
        grilledChickenTacos.setServings(4);
        grilledChickenTacos.setDescription("Grilled chicken tacos");
        grilledChickenTacos.setCategories(Set.of(categoryRepository.findByDescription("Mexican").get()));

        Ingredient anchoChiliPowder = new Ingredient();
        anchoChiliPowder.setDescription("ancho chili powder");
        anchoChiliPowder.setAmount(BigDecimal.valueOf(2));
        anchoChiliPowder.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Tablespoon").get());
        anchoChiliPowder.setRecipe(grilledChickenTacos);

        Ingredient driedOregano = new Ingredient();
        driedOregano.setDescription("dried oregano");
        driedOregano.setAmount(BigDecimal.valueOf(1));
        driedOregano.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        driedOregano.setRecipe(grilledChickenTacos);

        Ingredient driedCumin = new Ingredient();
        driedCumin.setDescription("dried cumin");
        driedCumin.setAmount(BigDecimal.valueOf(1));
        driedCumin.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        driedCumin.setRecipe(grilledChickenTacos);

        Ingredient sugar = new Ingredient();
        sugar.setDescription("sugar");
        sugar.setAmount(BigDecimal.valueOf(1));
        sugar.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        sugar.setRecipe(grilledChickenTacos);

        Ingredient salt = new Ingredient();
        salt.setDescription("salt");
        salt.setAmount(BigDecimal.valueOf(1 / 2));
        salt.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        salt.setRecipe(grilledChickenTacos);


        Ingredient garlic = new Ingredient();
        garlic.setDescription("garlic");
        garlic.setAmount(BigDecimal.valueOf(1));
        garlic.setUnitOfMeasure(unitOfMeasureRepository.findByDescription("Clove").get());
        garlic.setRecipe(grilledChickenTacos);

        grilledChickenTacos.getIngredients().addAll(Set.of(
                anchoChiliPowder, driedOregano, driedCumin, sugar, salt, garlic
        ));

        grilledChickenTacos.setDifficulty(Difficulty.MODERATE);

        grilledChickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        Notes notes = new Notes();
        notes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online.");
        notes.setRecipe(grilledChickenTacos);

        grilledChickenTacos.setNotes(notes);
        grilledChickenTacos.setSource("Simply Recipes");
        grilledChickenTacos.setDirections("\n" +
                "Prepare a gas or charcoal grill for medium-high, direct heat\n" +
                "Make the marinade and coat the chicken");

        return new ArrayList<Recipe>(List.of(grilledChickenTacos));
    }
}
