package org.aist.aide.generaltypeservice.domain.strategies;

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

    //
    @Override
    public String getType(String str) {
        var type = "String";
        if(str == null || str.isEmpty()) {
            return type;
        }
        var patterns = patternRepository.findAll();
        for(var pattern: patterns) {
            if (Pattern.matches(pattern.getPattern(), str)) {
                return pattern.getType();
            }
        }
        return type;
    }
}
