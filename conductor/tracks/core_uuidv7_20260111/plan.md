# Plan: Core UUIDv7 Engine & ArchUnit Hardening

## Phase 1: Project Scaffolding [checkpoint: 02e04cd]

- [x] Task: Initialize Gradle project with Java 21, Kotlin DSL, and Spring Boot 3.5.9 a859fa2

- [x] Task: Configure build.gradle.kts with dependencies: Spring Data MongoDB, ArchUnit, JUnit 5, jqwik, Pitest, and Testcontainers a859fa2

- [x] Task: Conductor - User Manual Verification 'Phase 1: Project Scaffolding' (Protocol in workflow.md) 02e04cd

## Phase 2: UUIDv7 Generation Engine

- [ ] Task: Write Failing Tests for UUIDv7 generation (Ordering, Bit layout, Entropy)
- [ ] Task: Implement UUIDv7 Generator with SecureRandom and Millisecond Timestamp
- [ ] Task: Write Failing Tests for Base16 Encoding/Decoding
- [ ] Task: Implement Hexadecimal utilities for logging and debugging
- [ ] Task: Conductor - User Manual Verification 'Phase 2: UUIDv7 Generation Engine' (Protocol in workflow.md)

## Phase 3: MongoDB Persistence & Converters

- [ ] Task: Write Failing Tests for UUIDv7 to MongoDB BinData conversion
- [ ] Task: Implement Custom WritingConverter and ReadingConverter for Spring Data MongoDB
- [ ] Task: Write Failing Integration Test using Testcontainers to verify index efficiency and storage
- [ ] Task: Conductor - User Manual Verification 'Phase 3: MongoDB Persistence & Converters' (Protocol in workflow.md)

## Phase 4: ArchUnit Hardening (RLS Enforcement)

- [ ] Task: Write Failing ArchUnit tests to detect repository methods lacking UserId
- [ ] Task: Implement ArchUnit rules for "Hardened Signature" enforcement
- [ ] Task: Write Failing Tests for Query Buffer strategy (Max Skew Window)
- [ ] Task: Implement Query Buffer logic with transparent adjustment and logging
- [ ] Task: Conductor - User Manual Verification 'Phase 4: ArchUnit Hardening' (Protocol in workflow.md)

## Phase 5: Finalization & Quality Gates

- [ ] Task: Execute PiTest for mutation testing and verify >80% coverage
- [ ] Task: Finalize README and usage documentation for Enterprise consumers
- [ ] Task: Conductor - User Manual Verification 'Phase 5: Finalization & Quality Gates' (Protocol in workflow.md)
