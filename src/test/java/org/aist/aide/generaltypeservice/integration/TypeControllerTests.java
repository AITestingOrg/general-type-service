package org.aist.aide.generaltypeservice.integration;

import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.aist.aide.generaltypeservice.service.controllers.TypeController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TypeControllerTests {
    @Autowired
    private TypeController typeController;

    @After
    public void tearDown() {
        var patterns = typeController.getPatterns();
        for (var pattern: patterns.getBody()) {
            typeController.deletePattern(pattern.getId());
        }
    }

    // Get All

    @Test
    public void givenNoPatternExists_WhenGettingAllPatterns_EmptyListIsReturned() {
        // Act
        var response = typeController.getPatterns();

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(0, response.getBody().size());
    }

    // Delete

    @Test
    public void givenAPatternExists_WhenDeletePattern_PatternIsDeleted() {
        // Arrange
        typeController
                .createPattern(
                        new Pattern("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$",
                                "SSN"));
        var pattern = typeController.getPattern("SSN").getBody();

        // Act
        var response = typeController.deletePattern(pattern.getId());

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(HttpStatus.NOT_FOUND, typeController.getPattern("SSN").getStatusCode());
    }

    @Test
    public void givenAPatternDoesNotExists_WhenDeletePattern_NotFoundReturned() {
        // Act
        var response = typeController.deletePattern("SSN");

        // Assert
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Get

    @Test
    public void givenAPatternExists_WhenGetPattern_PatternIsFound() {
        // Arrange
        typeController
                .createPattern(
                        new Pattern("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$",
                                "SSN"));

        // Act
        var response = typeController.getPattern("SSN");

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("SSN", response.getBody().getType());
    }

    @Test
    public void givenAPatternDoesNotExists_WhenGetPattern_NotFoundReturned() {
        // Act
        var response = typeController.getPattern("SSN");

        // Assert
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Update

    @Test
    public void givenAPatternExists_WhenUpdatePattern_PatternIsUpdated() {
        // Arrange
        typeController
                .createPattern(
                        new Pattern("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$",
                                "SSN"));

        var pattern = typeController.getPattern("SSN").getBody();
        var updatedPattern = new Pattern(pattern.getId(), "Phone", "Phone");

        // Act
        var response = typeController.updatePattern(updatedPattern);
        pattern = typeController.getPattern("Phone").getBody();

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Phone", pattern.getPattern());
        Assert.assertEquals("Phone", pattern.getType());
        Assert.assertEquals(updatedPattern.getId(), pattern.getId());
    }

    @Test
    public void givenAPatternDoesNotExists_WhenUpdatePattern_NotFoundReturned() {
        // Act
        var response = typeController.updatePattern(new Pattern("lskdjflk", "SSN", "SSN"));

        // Assert
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void givenAPatternExists_WhenGetPatternAndPatternTypeIsDuplicated_ValidationError() {
        typeController
                .createPattern(
                        new Pattern("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$",
                                "SSN"));

        typeController
                .createPattern(
                        new Pattern("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$",
                                "Phone"));

        var pattern = typeController.getPattern("Phone").getBody();
        var updatedPattern = new Pattern(pattern.getId(), "SSN", "SSN");

        // Act
        var response = typeController.updatePattern(updatedPattern);

        // Assert
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
