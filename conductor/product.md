# Initial Concept

ID Generator that generates secondary indexes for MongoDB

# Product Guide: MongoDB ID & Index Architect

## Product Vision

A high-performance Java Enterprise library designed for backend developers to solve the critical problem of index fragmentation and performance degradation in MongoDB. By focusing on **UUIDv7**—a time-ordered, BSON-optimized ID format—alongside static secondary index generation via Java source code and annotations, this tool ensures database efficiency and consistency across microservices.

## Target Audience

* **Backend Developers:** Building high-scale MongoDB applications where write performance and index efficiency are paramount.

## Core Features

* **UUIDv7 ID Generation:** Specialized support for UUIDv7, providing time-ordered, unique identifiers that minimize MongoDB BSON index fragmentation and page splits.

* **Static Index Generation:** Automated generation of Java source code and Spring Data MongoDB annotations (`@Indexed`, `@CompoundIndex`) to ensure index definitions are version-controlled and consistent.

* **Microservice Integration:** Distributed as a Java library (JAR) for seamless integration into enterprise frameworks like Spring Boot or Micronaut.

### ID Generator Strategy

* **Bit Container Strategy:** Implements a strict 128-bit container layout: Partition 1 (Timestamp, bits 0-47, K-ordered) and Partition 2 (Entropy, bits 48-127, using SecureRandom CSPNG).

* **Collision Resistance:** Leverages an 80-bit entropy partition, providing a search space of $2^{80}$ and a collision threshold of ~1 trillion generations per millisecond.

* **Encoding:** Utilizes Base16 (Hexadecimal) encoding (32 characters) for efficiency, translating to 4 bits per character.
  
  ### Storage & Persistence

* **Optimized Storage:** Enforces conversion of the 32-character Hexadecimal string to MongoDB `BinData` (Subtype) before storage. Storing as a raw Hexadecimal string is prohibited.

* **Display Logic:** Hexadecimal representation is reserved exclusively for logging and debugging purposes.
  
  ### Reliability & Security

* **NTP Drift Mitigation:** Implements a Query Buffer strategy, querying up to `Now - Max_Skew_Window` (e.g., 5 seconds) to handle clock skew, explicitly prohibiting queries "up to the millisecond."

* **IDOR Protection:** Enforces strict internal access only. Database IDs must never be exposed to clients or appear in API URLs.

* **Row-Level Security (RLS):** Mandates "Hardened Signatures" at the application level. Repository methods must include `UserId` (e.g., `findByPublicIdAndUserId`). Compliance is enforced via automated code review lints (ArchUnit).
  
  ## Technical Problem Statement
  
  Modern high-scale MongoDB applications suffer from performance bottlenecks when using random UUIDs, which cause heavy index fragmentation. UUIDv7 provides a standardized, time-ordered alternative. This library automates the generation of these high-performance IDs and the formal definition of the secondary indexes required to support them, reducing manual errors and architectural drift.
