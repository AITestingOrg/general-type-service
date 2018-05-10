package org.aist.aide.generaltypeservice.service.repositories;

import java.util.Optional;
import java.util.UUID;

import org.aist.aide.generaltypeservice.domain.models.Pattern;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatternRepository extends MongoRepository<Pattern, UUID> {
    Optional<Pattern> findByType(String type);
}
