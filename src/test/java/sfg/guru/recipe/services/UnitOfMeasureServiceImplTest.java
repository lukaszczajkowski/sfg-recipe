package sfg.guru.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import sfg.guru.recipe.commands.UnitOfMeasureCommand;
import sfg.guru.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import sfg.guru.recipe.domain.UnitOfMeasure;
import sfg.guru.recipe.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasureService service;

    @Before
    public void setUp() throws Exception {
        openMocks(this);

        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);

        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>(Set.of(uom1, uom2));

        when(unitOfMeasureRepository.findAll())
                .thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        assertThat(commands, hasSize(2));
        verify(unitOfMeasureRepository).findAll();
    }
}