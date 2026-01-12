package com.mongodb.id.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

class Uuidv7GeneratorTest {

    private final Uuidv7Generator generator = new Uuidv7Generator();

    @Test
    @DisplayName("Should generate a UUID with version 7")
    void shouldGenerateVersion7() {
        UUID uuid = generator.generate();
        assertEquals(7, uuid.version());
    }

    @Test
    @DisplayName("Should generate a UUID with RFC 4122 variant")
    void shouldGenerateVariant2() {
        UUID uuid = generator.generate();
        assertEquals(2, uuid.variant());
    }

    @Test
    @DisplayName("Should generate UUIDs in increasing order")
    void shouldBeMonotonicallyIncreasing() throws InterruptedException {
        UUID u1 = generator.generate();
        Thread.sleep(2); // Ensure different millisecond
        UUID u2 = generator.generate();
        
        assertTrue(u1.compareTo(u2) < 0, "UUIDs should be monotonically increasing");
    }

    @Test
    @DisplayName("Should generate unique IDs even within the same millisecond")
    void shouldBeUniqueWithinSameMillisecond() {
        UUID u1 = generator.generate();
        UUID u2 = generator.generate();
        
        assertNotEquals(u1, u2);
        
        // If timestamps are same, entropy must differ
        long ts1 = u1.getMostSignificantBits() >>> 16;
        long ts2 = u2.getMostSignificantBits() >>> 16;
        
        if (ts1 == ts2) {
            assertNotEquals(u1.getLeastSignificantBits(), u2.getLeastSignificantBits(), "Entropy should differ");
        }
    }
}
