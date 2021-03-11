package sfg.guru.recipe.converters;

import org.junit.Before;
import org.junit.Test;
import sfg.guru.recipe.commands.UnitOfMeasureCommand;
import sfg.guru.recipe.domain.UnitOfMeasure;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void shouldConvert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(LONG_VALUE);
        unitOfMeasure.setDescription(DESCRIPTION);

        UnitOfMeasureCommand actual = converter.convert(unitOfMeasure);

        assertNotNull(actual);
        assertEquals(LONG_VALUE, actual.getId());
        assertEquals(DESCRIPTION, actual.getDescription());
    }
}