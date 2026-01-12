# Plan: Core UUIDv7 Engine & ArchUnit Hardening

## Phase 1: Project Scaffolding [checkpoint: 02e04cd]

- [x] Task: Initialize Gradle project with Java 21, Kotlin DSL, and Spring Boot 3.5.9 a859fa2

- [x] Task: Configure build.gradle.kts with dependencies: Spring Data MongoDB, ArchUnit, JUnit 5, jqwik, Pitest, and Testcontainers a859fa2

- [x] Task: Conductor - User Manual Verification 'Phase 1: Project Scaffolding' (Protocol in workflow.md) 02e04cd

## Phase 2: UUIDv7 Generation Engine [checkpoint: 286b661]

- [x] Task: Write Failing Tests for UUIDv7 generation (Ordering, Bit layout, Entropy) 66d1a0d
- [x] Task: Implement UUIDv7 Generator with SecureRandom and Millisecond Timestamp 66d1a0d
- [x] Task: Write Failing Tests for Base16 Encoding/Decoding ad4acca
- [x] Task: Implement Hexadecimal utilities for logging and debugging ad4acca
- [x] Task: Conductor - User Manual Verification 'Phase 2: UUIDv7 Generation Engine' (Protocol in workflow.md) 286b661

## Phase 3: MongoDB Persistence & Converters [checkpoint: beb4ffb]

- [x] Task: Write Failing Tests for UUIDv7 to MongoDB BinData conversion 8f44dd9
- [x] Task: Implement Custom WritingConverter and ReadingConverter for Spring Data MongoDB 8f44dd9
- [x] Task: Write Failing Integration Test using Testcontainers to verify index efficiency and storage beb4ffb
- [x] Task: Conductor - User Manual Verification 'Phase 3: MongoDB Persistence & Converters' (Protocol in workflow.md) beb4ffb

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
