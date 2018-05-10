package org.aist.aide.generaltypeservice.unit;

import org.aist.aide.generaltypeservice.domain.exceptions.NotFoundException;
import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.aist.aide.generaltypeservice.domain.services.PatternCrudService;
import org.aist.aide.generaltypeservice.service.repositories.PatternRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class PatternCrudServiceTests {
    @Spy
    private PatternRepository patternRepository;

    @InjectMocks
    private PatternCrudService patternCrudService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAPatternExists_WhenRequestingThePattern_ThePatternIsReturned() throws NotFoundException {
        // arrange
        var type = "Phone";
        var pattern = new Pattern("regex", type);
        when(patternRepository.findByType(type)).thenReturn(Optional.of(pattern));

        // act
        var patternFound = patternCrudService.getPattern(type);

        // assert
        Assert.assertEquals(pattern, patternFound);
    }
}
