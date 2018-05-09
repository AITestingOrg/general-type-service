package org.aist.aide.generaltypeservice.domain.strategies;

import org.aist.aide.generaltypeservice.domain.models.Types;
import org.aist.aide.generaltypeservice.service.repositories.PatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class RegexTypeResolutionStrategy implements TypeResolutionStrategy {
    private PatternRepository patternRepository;

    public RegexTypeResolutionStrategy(@Autowired PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    // ^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}$
    @Override
    public Types getType(String str) {
        var patterns = patternRepository.findAll();
        var type = Types.STRING;
        for(var pattern: patterns) {
            if (Pattern.matches(pattern.getPattern(), str)) {
                return pattern.getType();
            }
        }
        return type;
    }
}
