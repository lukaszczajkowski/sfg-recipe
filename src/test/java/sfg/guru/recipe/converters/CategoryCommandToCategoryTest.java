package sfg.guru.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sfg.guru.recipe.commands.CategoryCommand;
import sfg.guru.recipe.domain.Category;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "description";

    private CategoryCommandToCategory converter;

    private CategoryCommand categoryCommand;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
        categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID);
        categoryCommand.setDescription(DESCRIPTION);
    }

    @Test
    public void shouldConvert() {
        Category actual = converter.convert(categoryCommand);

        assertNotNull(actual);
        assertEquals(ID, actual.getId());
        assertEquals(DESCRIPTION, actual.getDescription());
    }

    @Test
    public void shouldReturnNullWhenNullObjectPassed() {
        Category actual = converter.convert(null);

        assertNull(actual);
    }

    @Test
    public void shouldNotReturnNullWithEmptyObject() {
        Category actual = converter.convert(new CategoryCommand());

        assertNotNull(actual);
    }
}