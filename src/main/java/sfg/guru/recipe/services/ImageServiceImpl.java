package sfg.guru.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sfg.guru.recipe.domain.Recipe;
import sfg.guru.recipe.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        try {
            Recipe recipe = recipeRepository.findById(id).get();

            byte[] source = file.getBytes();
            Byte[] byteObjects = new Byte[file.getBytes().length];

            IntStream.range(0, source.length)
                    .boxed()
                    .forEach(i -> byteObjects[i] = source[i]);

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            //todo handle better
            log.error("Error occured", e);

            e.printStackTrace();
        }
    }
}
