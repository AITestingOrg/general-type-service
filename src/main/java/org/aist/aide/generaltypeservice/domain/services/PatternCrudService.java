package org.aist.aide.generaltypeservice.domain.services;

import java.util.List;
import java.util.logging.Logger;

import org.aist.aide.formexpert.common.exceptions.NotFoundException;
import org.aist.aide.formexpert.common.exceptions.ValidationFailureException;
import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.aist.aide.generaltypeservice.service.repositories.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatternCrudService {
    private static final Logger LOGGER = Logger.getLogger(PatternCrudService.class.getName());
    private PatternRepository patternRepository;

    public PatternCrudService(@Autowired PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    public Pattern getPattern(String type) throws NotFoundException {
        var pattern = patternRepository.findByType(type);
        if (pattern.isPresent()) {
            return pattern.get();
        }
        LOGGER.warning(String.format("Pattern of type %s does not exist.", type));
        throw new NotFoundException(String.format("Failed to find pattern for type %s.", type));
    }

    public List<Pattern> getPatterns() {
        return patternRepository.findAll();
    }

    public String createPattern(Pattern pattern) throws ValidationFailureException {
        var existingPattern = patternRepository.findByType(pattern.getType().toString());
        if (existingPattern.isPresent()) {
            LOGGER.warning(String.format("Pattern of type %s already exists.", pattern.getType().toString()));
            throw new ValidationFailureException(String.format(
                    "Pattern matching type %s already exists, cannot create.",
                    pattern.getType().toString()));
        }
        patternRepository.save(pattern);
        return pattern.getId();
    }

    public void deletePattern(String id) throws NotFoundException {
        var pattern = patternRepository.findById(id);
        if (!pattern.isPresent()) {
            LOGGER.warning(String.format("Pattern with id %s does not exist, cannot delete.", id));
            throw new NotFoundException(String.format("No pattern found with id %s", id));
        }
        patternRepository.delete(pattern.get());
    }

    public void updatePattern(Pattern pattern) throws NotFoundException, ValidationFailureException {
        var patternToUpdate = patternRepository.findById(pattern.getId());
        if (!patternToUpdate.isPresent()) {
            LOGGER.warning(String.format("Pattern with id %s does not exist, cannot update.", pattern.getId()));
            throw new NotFoundException(String.format("No pattern found with id %s", pattern.getId()));
        }
        patternToUpdate = patternRepository.findByType(pattern.getType());
        if (patternToUpdate.isPresent() && patternToUpdate.get().getId() != pattern.getId()) {
            LOGGER.warning(String.format("Pattern of type %s already exists.", pattern.getType()));
            throw new ValidationFailureException(String.format(
                    "Pattern matching type %s already exists, cannot create.",
                    pattern.getType().toString()));
        }
        patternRepository.save(pattern);
    }
}
