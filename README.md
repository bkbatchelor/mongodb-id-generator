# MongoDB ID & Index Architect

Enterprise-grade UUIDv7 generation and Spring Data MongoDB integration library for time-ordered identifiers and hardened security enforcement.

## Features

- **UUIDv7 Engine:** K-ordered, time-based 128-bit identifiers with 48-bit millisecond precision and 74-bit cryptographically strong entropy.
- **BSON Optimization:** Automatic conversion to MongoDB `BinData` (Subtype 4) ensuring byte-order preservation for efficient index traversals.
- **ArchUnit Hardening:** Build-time enforcement of Row Level Security (RLS) patterns, ensuring all repository methods include a `UserId` to prevent IDOR vulnerabilities.
- **Clock Skew Resilience:** Transparent query buffer strategy to handle up to 5 seconds of clock drift across distributed nodes.

## Requirements

- Java 21+
- Spring Boot 3.5.9+
- MongoDB 7.0+ (Recommended for optimized BinData indexing)

## Installation

```kotlin
implementation("io.sandboxdev.id.generator:mongodb-id-generator:0.0.1-SNAPSHOT")
```

## Usage

### 1. Generating UUIDv7

```java
Uuidv7Generator generator = new Uuidv7Generator();
UUID id = generator.generate();
```

### 2. Spring Data MongoDB Integration

Register the converters in your configuration:

```java
@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
            new Uuidv7ToBinaryConverter(),
            new BinaryToUuidv7Converter()
        ));
    }
}
```

### 3. Hardened Repositories (RLS)

All repository methods must accept a `UserId` to pass build-time ArchUnit checks:

```java
public interface OrderRepository extends MongoRepository<Order, UUID> {
    List<Order> findByStatus(String status, UserId userId);
}
```

### 4. Query Buffer for Time Ranges

```java
QueryBuffer buffer = new QueryBuffer(5000); // 5s skew allowance
UUID start = buffer.getStartUuid(startTime);
UUID end = buffer.getEndUuid(endTime);

// Use in mongoTemplate.find() with Criteria.where("id").gte(start).lte(end)
```

## Security

The library enforces the "Hardened Signature" pattern. ArchUnit will fail the build if any `MongoRepository` method is detected without a `UserId` parameter, significantly reducing the risk of accidental data exposure.

## License

Apache License 2.0
