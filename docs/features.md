# ID Generator Features

## ID Space Strategy

### Bit Container

MUST use a 128-bit container according to the partition layout

- **Partition 1 (Timestamp):** Bits 0 - 47
  
- **Partition 2 (Entropy/Randomness):** Bits 48 - 127
  

#### Partition implementation

MUST implement Partition with:

- **Partition 1 (Timestamp):** K-Ordering
  
- **Partition 2 (Entropy/Randomness):** CSPNG (SecureRandom)
  
  Cryptographically Secure Pseudorandom Number Generator
  

### Encoding to 32 characters

Hexadecimal (Base16):

- Efficiency: 4 bits per char.
  
- Length: $128 / 4 = 32$ characters.
  

## Collision Strategy

The 128-bit ID space sufficiently large to render collisions statistically impossible

- **Bits 48-127:** Randomness (80 bits)
  
- **Search Space:** $2^{80}$
  
- **Collision threshold:** $2^{40}$ (~1 trillion) generations *per millisecond*.
  

## Storage Strategy

Before storing, MUST convert the 32-chars Hexadecimal (32 bytes) to MongoDB BinData (Subtype) Type

- MUST NOT store Hexadecimal string

SHALL display as a Hexadecimal ONLY:

- Reading or writing to logs
  
- Debugging
  

## NTP Drift & Clock Skew Strategy

MUST implement a Query Buffer 

- MUST query up to `Now - Max_Skew_Window` (e.g., 5 seconds ago)
  
- MUST NOT not query`up to the millisecond.`
  

## Insecure Direct Object Reference (IDOR) Exposure Strategy

The ID Generator SHALL only be accessed by internal network services.

- Database IDs are SHALL NOT exposed to the client.
  
- Database IDs SHALL NOT appear in the Client API URLs
  

## Row Level Security (RLS) Strategy

MUST implement a  Hardened Signature at application level

- Every repository method MUST include the `UserId`

```java
// Hardened Code (Mandatory)
Optional<Order> findByPublicIdAndUserId(UUID generatedId, Long userId);
```

```java
// Weak Code (Banned):
Optional<Order> findByPublicId(UUID publicId);
```

MUST enforce `findByIdAndUser` via code review lints (ArchUnit).
