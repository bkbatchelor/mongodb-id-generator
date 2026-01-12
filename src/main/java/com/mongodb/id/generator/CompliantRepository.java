package com.mongodb.id.generator;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.UUID;

/**
 * A compliant repository for testing ArchUnit enforcement.
 */
public interface CompliantRepository extends MongoRepository<Object, UUID> {
    
    // Passes because it includes a UserId parameter
    List<Object> findByName(String name, UserId userId);
}
