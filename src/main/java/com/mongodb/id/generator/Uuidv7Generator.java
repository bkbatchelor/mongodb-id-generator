package com.mongodb.id.generator;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Generates UUIDv7 identifiers according to RFC 9562.
 * UUIDv7 is a time-ordered UUID that provides a 48-bit millisecond precision timestamp
 * followed by 74 bits of entropy.
 */
public class Uuidv7Generator {
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a new UUIDv7.
     *
     * @return a new UUIDv7 instance
     */
    public UUID generate() {
        long timestamp = System.currentTimeMillis();
        
        // msb: 48 bits timestamp | 4 bits version (7) | 12 bits entropy (rand_a)
        long msb = (timestamp & 0xFFFFFFFFFFFFL) << 16;
        msb |= (7L << 12);
        msb |= (RANDOM.nextLong() & 0x0FFFL);
        
        // lsb: 2 bits variant (2) | 62 bits entropy (rand_b)
        long lsb = 0x8000000000000000L;
        lsb |= (RANDOM.nextLong() & 0x3FFFFFFFFFFFFFFFL);
        
        return new UUID(msb, lsb);
    }
}
