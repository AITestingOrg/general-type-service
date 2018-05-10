package org.aist.aide.generaltypeservice.unit;

import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.aist.aide.generaltypeservice.domain.strategies.RegexTypeResolutionStrategy;
import org.aist.aide.generaltypeservice.service.repositories.PatternRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class RegexTypeResolutionStrategyTest {
    private static final String str = "String";
    @Mock
    private PatternRepository patternRepository;

    @InjectMocks
    private RegexTypeResolutionStrategy regexTypeResolutionStrategy;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Happy Path tests
     */
    @Test
    public void givenAMatchableRegex_WhenCalledWithMatchingInput_ValidTypeReturned() {
        // arrange
        var patterns = new ArrayList<Pattern>() {};
        var phone = "Phone";
        patterns.add(new Pattern("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", phone));
        when(patternRepository.findAll()).thenReturn(patterns);

        // act
        var type = regexTypeResolutionStrategy.getType("(954) 225-9722");

        // assert
        Assert.assertEquals(type, phone);
    }

    @Test
    public void givenAMatchableRegex_WhenCalledWithNonMatchingInput_TypeFallsBackToString() {
        // arrange
        var patterns = new ArrayList<Pattern>() {};
        patterns.add(new Pattern("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", "Phone"));
        when(patternRepository.findAll()).thenReturn(patterns);

        // act
        var type = regexTypeResolutionStrategy.getType("wrgwagawgah");

        // assert
        Assert.assertEquals(type, str);
    }

    @Test
    public void givenAMatchableRegex_WhenCalledWithNullInput_TypeFallsBackToString() {
        // arrange
        var patterns = new ArrayList<Pattern>() {};
        patterns.add(new Pattern("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", "Phone"));
        when(patternRepository.findAll()).thenReturn(patterns);

        // act
        var type = regexTypeResolutionStrategy.getType(null);

        // assert
        Assert.assertEquals(type, str);
    }

    @Test
    public void givenAMatchableRegex_WhenCalledWithEmptyInput_TypeFallsBackToString() {
        // arrange
        var patterns = new ArrayList<Pattern>() {};
        patterns.add(new Pattern("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", "Phone"));
        when(patternRepository.findAll()).thenReturn(patterns);

        // act
        var type = regexTypeResolutionStrategy.getType("");

        // assert
        Assert.assertEquals(type, str);
    }
}
