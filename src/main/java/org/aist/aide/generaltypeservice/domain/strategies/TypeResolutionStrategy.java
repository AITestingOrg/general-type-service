package org.aist.aide.generaltypeservice.domain.strategies;

import org.aist.aide.generaltypeservice.domain.models.Types;

public interface TypeResolutionStrategy {
    Types getType(String str);
}
