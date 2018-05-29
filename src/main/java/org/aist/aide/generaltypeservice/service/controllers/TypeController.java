package org.aist.aide.generaltypeservice.service.controllers;

import java.util.List;
import java.util.logging.Logger;
import javax.validation.Valid;

import org.aist.aide.formexpert.common.exceptions.NotFoundException;
import org.aist.aide.formexpert.common.exceptions.ValidationFailureException;
import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.aist.aide.generaltypeservice.domain.services.PatternCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/type")
public class TypeController {
    private static final Logger LOGGER = Logger.getLogger(TypeController.class.getName());
    private PatternCrudService patternCrudService;

    public TypeController(@Autowired PatternCrudService patternCrudService) {
        this.patternCrudService = patternCrudService;
    }

    @RequestMapping("/{type}")
    public ResponseEntity<Pattern> getPattern(@RequestParam String type) {
        LOGGER.info(String.format("GET request for Pattern of type %s.", type));
        try {
            var pattern = patternCrudService.getPattern(type);
            return new ResponseEntity<>(pattern, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/")
    public ResponseEntity<List<Pattern>> getPatterns() {
        LOGGER.info(String.format("GET request for all Patterns."));
        var patterns = patternCrudService.getPatterns();
        return new ResponseEntity<>(patterns, HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity createPattern(@Valid @RequestBody Pattern pattern) {
        LOGGER.info(String.format("POST request with Pattern %s.", pattern));
        try {
            patternCrudService.createPattern(pattern);
        } catch (ValidationFailureException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePattern(@RequestParam String id) {
        LOGGER.info(String.format("DELETE request for Pattern with id %s.", id));
        try {
            patternCrudService.deletePattern(id);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.PUT)
    public ResponseEntity updatePattern(@Valid @RequestBody Pattern pattern) {
        LOGGER.info(String.format("PUT request for Pattern %s.", pattern));
        try {
            patternCrudService.updatePattern(pattern);
        } catch (NotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (ValidationFailureException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
