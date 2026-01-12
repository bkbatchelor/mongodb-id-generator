# Tech Stack: MongoDB ID & Index Architect

## Foundation

* **Language:** Java 21 (LTS) - Utilizing modern features like Record patterns and improved performance.

* **Framework:** Spring Boot 3.5.9 - Using the Spring Boot Bill of Materials (BOM) for dependency management.

* **Spring Data:** Spring Data MongoDB (compatible with Spring Boot 3.5.9) - Provides the underlying abstraction for document mapping and repository support.

* **Build System:** Gradle with Kotlin DSL - For expressive, type-safe build automation and dependency management.

## Core Database Integration

* **ID Strategy:** Custom UUIDv7 implementation - Built within the library to ensure adherence to the 128-bit partition layout (Timestamp + SecureRandom Entropy).

## Quality & Security Enforcement

* **Architectural Lints:** ArchUnit - Used to enforce the "Hardened Signature" rule, ensuring all repository methods include a `UserId`.

* **Unit Testing:** JUnit 5 - The standard for creating composable and comprehensive unit tests.

* **Property-Based Testing:** jqwik - To rigorously test ID generation properties (e.g., ordering, uniqueness) across a vast range of inputs.

* **Integration Testing:** AssertJ (Fluent Assertions) + Testcontainers (MongoDB) - Ensures the library's ID generation and index strategies are validated against actual MongoDB instances during the build lifecycle.

* **Mutation Testing:** PiTest - To measure test quality by ensuring tests fail when code is modified (verifying test robustness).

## Deployment & Distribution

* **Artifact:** Java Archive (JAR) - Optimized for distribution as a library for microservices.
