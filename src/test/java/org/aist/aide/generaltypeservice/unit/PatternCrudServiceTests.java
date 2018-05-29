package org.aist.aide.generaltypeservice.unit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.aist.aide.generaltypeservice.domain.exceptions.NotFoundException;
import org.aist.aide.generaltypeservice.domain.exceptions.ValidationFailureException;
import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.aist.aide.generaltypeservice.domain.services.PatternCrudService;
import org.aist.aide.generaltypeservice.service.repositories.PatternRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatternCrudServiceTests {
    @Mock
    private PatternRepository patternRepository;

    @InjectMocks
    private PatternCrudService patternCrudService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAPatternExists_WhenRequestingThePattern_ThePatternIsReturned()
            throws NotFoundException {
        // arrange
        var type = "Phone";
        var pattern = new Pattern("regex", type);
        when(patternRepository.findByType(type)).thenReturn(Optional.of(pattern));

        // act
        var patternFound = patternCrudService.getPattern(type);

        // assert
        Assert.assertEquals(pattern, patternFound);
    }

    @Test(expected = NotFoundException.class)
    public void givenAPatternDoesNotExists_WhenRequestingThePattern_NotFoundExceptionThrown()
            throws NotFoundException {
        // arrange
        var type = "Phone";
        when(patternRepository.findByType(type)).thenReturn(Optional.ofNullable(null));

        // act
        var patternFound = patternCrudService.getPattern(type);
    }

    @Test
    public void givenAPatternExists_WhenRequestingAllPatterns_ThePatternIsReturned() {
        // arrange
        var type = "Phone";
        var patterns = new ArrayList<Pattern>();
        var pattern = new Pattern("regex", type);
        patterns.add(pattern);
        when(patternRepository.findAll()).thenReturn(patterns);

        // act
        var patternsFound = patternCrudService.getPatterns();

        // assert
        Assert.assertEquals(1, patterns.size());
        Assert.assertEquals(pattern, patterns.get(0));
    }

    @Test
    public void givenPatternsExists_WhenRequestingAllPatterns_ThePatternCountIsEqual() {
        // arrange
        var patterns = new ArrayList<Pattern>();
        patterns.add(new Pattern("regex", "Test"));
        patterns.add(new Pattern("regex", "Phone"));
        when(patternRepository.findAll()).thenReturn(patterns);

        // act
        var patternsFound = patternCrudService.getPatterns();

        // assert
        Assert.assertEquals(2, patternsFound.size());
    }

    @Test
    public void givenNoPatternExists_WhenRequestingAllPatterns_NoPatternsAreReturned() {
        // arrange
        var patterns = new ArrayList<Pattern>();
        when(patternRepository.findAll()).thenReturn(patterns);

        // act
        var patternsFound = patternCrudService.getPatterns();

        // assert
        Assert.assertEquals(0, patternsFound.size());
    }

    @Test
    public void givenTheTypeDoesNotExist_WhenCreatingThePattern_ThePatternIsSaved()
            throws ValidationFailureException {
        // arrange
        var type = "Phone";
        var pattern = new Pattern("regex", type);
        when(patternRepository.findByType(type)).thenReturn(Optional.ofNullable(null));

        // act
        patternCrudService.createPattern(pattern);

        // assert
        verify(patternRepository, times(1)).save(pattern);
    }

    @Test(expected = ValidationFailureException.class)
    public void givenTheTypeExist_WhenCreatingThePattern_ValidationExceptionIsThrown()
            throws ValidationFailureException {
        // arrange
        var type = "Phone";
        var pattern = new Pattern("regex", type);
        when(patternRepository.findByType(type)).thenReturn(Optional.ofNullable(pattern));

        // act
        patternCrudService.createPattern(pattern);
    }

    @Test
    public void givenTheTypeExist_WhenDeletingThePattern_PatternIsDeleted() throws NotFoundException {
        // arrange
        var type = "Phone";
        var pattern = new Pattern("#$%^","regex", type);
        when(patternRepository.findById(pattern.getId())).thenReturn(Optional.ofNullable(pattern));

        // act
        patternCrudService.deletePattern(pattern.getId());

        //assert
        verify(patternRepository, times(1)).delete(pattern);
    }

    @Test(expected = NotFoundException.class)
    public void givenTheTypeDoesNotExist_WhenDeletingThePattern_NotFoundExceptionThrown()
            throws NotFoundException {
        // arrange
        var type = "Phone";
        var id = "#$%^";
        when(patternRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        // act
        patternCrudService.deletePattern(id);
    }

    @Test
    public void givenTheTypeExist_WhenUpdatingThePattern_PatternIsUpdated()
            throws ValidationFailureException, NotFoundException {
        // arrange
        var type = "Phone";
        var pattern = new Pattern("#$%^","regex", type);
        when(patternRepository.findById(pattern.getId())).thenReturn(Optional.of(pattern));
        var update = new Pattern(pattern.getId(), "regex", "Test");

        // act
        patternCrudService.updatePattern(update);

        //assert
        verify(patternRepository, times(1)).save(update);
    }

    @Test(expected = NotFoundException.class)
    public void givenTheTypeDoesNotExist_WhenUpdatingThePattern_NotFoundExceptionThrown()
            throws NotFoundException, ValidationFailureException {
        // arrange
        var type = "Phone";
        var pattern = new Pattern("#$%^","regex", type);
        when(patternRepository.findById(pattern.getId())).thenReturn(Optional.ofNullable(null));

        // act
        patternCrudService.updatePattern(pattern);
    }

    @Test(expected = ValidationFailureException.class)
    public void givenTheNewTypeExists_WhenUpdatingThePattern_ValidationExceptionThrown()
            throws NotFoundException, ValidationFailureException {
        // arrange
        var type = "Phone";
        var newType = "Test";
        var pattern = new Pattern("#$%^","regex", type);
        var otherPattern = new Pattern("#$%^245", "regex", newType);
        when(patternRepository.findById(pattern.getId())).thenReturn(Optional.of(pattern));
        when(patternRepository.findByType(newType)).thenReturn(Optional.of(otherPattern));
        var update = new Pattern(pattern.getId(), "regex", "Test");

        // act
        patternCrudService.updatePattern(update);
    }
}
