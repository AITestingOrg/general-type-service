package org.aist.aide.generaltypeservice.integration;

import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.aist.aide.generaltypeservice.service.controllers.TypeController;
import org.aist.aide.generaltypeservice.service.controllers.TypeResolutionController;
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
public class TypeResolutionControllerTests {
    @Autowired
    private TypeController typeController;

    @Autowired
    private TypeResolutionController typeResolutionController;

    private void createMappings() {
        typeController
                .createPattern(
                        new Pattern("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$",
                                "Phone"));
        typeController
                .createPattern(
                        new Pattern("^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$",
                                "SSN"));
    }

    @After
    public void tearDown() {
        var patterns = typeController.getPatterns();
        for (var pattern: patterns.getBody()) {
            typeController.deletePattern(pattern.getId());
        }
    }

    @Test
    public void givenAPhonePatternExists_WhenTextMatchesThePattern_TheTypeIsReturned() {
        // Arrange
        createMappings();

        // Act
        var response = typeResolutionController.resolveType("954-345-2642");

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("Phone", response.getBody().getType());
    }

    @Test
    public void givenASNNPatternExists_WhenTextMatchesThePattern_TheTypeIsReturned() {
        // Arrange
        createMappings();

        // Act
        var response = typeResolutionController.resolveType("954-45-2642");

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("SSN", response.getBody().getType());
    }

    @Test
    public void givenAPatternDoesNotExists_WhenTextMatchesThePattern_StringTypeReturned() {
        // Arrange
        createMappings();

        // Act
        var response = typeResolutionController.resolveType("@9545463573");

        // Assert
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("String", response.getBody().getType());
    }
}
