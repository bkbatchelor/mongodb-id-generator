# Specification: Core UUIDv7 Engine & ArchUnit Hardening

## Overview

This track implements the foundational ID generation logic for the MongoDB ID & Index Architect library, focusing on UUIDv7 compliance and architectural security enforcement (RLS) via ArchUnit.

## Functional Requirements

### 1. UUIDv7 Generation

- **Container:** 128-bit space.
- **Partition 1 (Timestamp):** Bits 0-47. Must be K-ordered based on millisecond precision.
- **Partition 2 (Entropy):** Bits 48-127 (80 bits). Must use `SecureRandom` (CSPNG).
- **Encoding:** Hexadecimal (Base16) string representation (32 characters) for logs and debugging.

### 2. MongoDB Persistence

- **Conversion:** Automatically convert 32-character Hex strings to MongoDB `BinData` (Subtype) before persistence.
- **BSON Optimization:** Ensure the byte order in `BinData` maintains the time-ordered property of UUIDv7 for efficient indexing.

### 3. Hardened Signature Enforcement (RLS)

- **Constraint:** All Spring Data MongoDB repository methods MUST include a `UserId` parameter to prevent IDOR vulnerabilities.
- **Enforcement:** Automated build-time checks using ArchUnit to fail the build if non-compliant methods are detected.

## Technical Constraints

- **Java 21** features (Records, etc.).
- **Spring Boot 3.5.9** and compatible Spring Data MongoDB.
- **Max Skew Window:** 5 seconds for query range adjustments.
