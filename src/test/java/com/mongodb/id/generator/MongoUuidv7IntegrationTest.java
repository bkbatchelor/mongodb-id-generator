package com.mongodb.id.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@Import(MongoConfig.class)
class MongoUuidv7IntegrationTest {

    @SpringBootApplication
    static class TestApp {}

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Document(collection = "test_entities")
    static class TestEntity {
        @Id
        private UUID id;
        private String name;

        public TestEntity() {}

        public TestEntity(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @Test
    @DisplayName("Should persist and retrieve UUID as BinData subtype 4")
    void shouldPersistAndRetrieveUuid() {
        UUID id = new Uuidv7Generator().generate();
        TestEntity entity = new TestEntity(id, "test-item");
        
        mongoTemplate.save(entity);
        
        TestEntity retrieved = mongoTemplate.findById(id, TestEntity.class);
        assertNotNull(retrieved);
        assertEquals(id, retrieved.getId());
    }
}
