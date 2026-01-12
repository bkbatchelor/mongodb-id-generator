package com.mongodb.id.generator;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.UUID;

/**
 * A non-compliant repository for testing ArchUnit enforcement.
 */
public interface NonCompliantRepository extends MongoRepository<Object, UUID> {
    
    // Fails because it lacks a UserId parameter
    List<Object> findByName(String name);
}
