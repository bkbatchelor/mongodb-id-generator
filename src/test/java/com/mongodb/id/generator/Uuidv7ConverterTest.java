package com.mongodb.id.generator;

import org.bson.types.Binary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class Uuidv7ConverterTest {

    private final Uuidv7ToBinaryConverter writingConverter = new Uuidv7ToBinaryConverter();
    private final BinaryToUuidv7Converter readingConverter = new BinaryToUuidv7Converter();

    @Test
    @DisplayName("Should convert UUID to BSON Binary (Subtype 4 or 0)")
    void shouldConvertUuidToBinary() {
        UUID uuid = UUID.randomUUID();
        Binary binary = writingConverter.convert(uuid);
        
        assertNotNull(binary);
        // MongoDB UUID representation is typically subtype 4
        assertEquals(4, binary.getType());
        assertEquals(16, binary.getData().length);
    }

    @Test
    @DisplayName("Should convert BSON Binary back to UUID")
    void shouldConvertBinaryToUuid() {
        UUID original = UUID.randomUUID();
        Binary binary = writingConverter.convert(original);
        UUID decoded = readingConverter.convert(binary);
        
        assertEquals(original, decoded);
    }

    @Test
    @DisplayName("Should maintain byte order for time-ordered indexing")
    void shouldMaintainByteOrder() {
        // Create two UUIDs with different timestamps
        UUID u1 = new UUID(0x018d1594e69f7000L, 0x8123456789abcdefL);
        UUID u2 = new UUID(0x018d1594e6a07000L, 0x8123456789abcdefL);
        
        Binary b1 = writingConverter.convert(u1);
        Binary b2 = writingConverter.convert(u2);
        
        // Compare byte arrays
        byte[] d1 = b1.getData();
        byte[] d2 = b2.getData();
        
        int comparison = 0;
        for (int i = 0; i < 16; i++) {
            int v1 = d1[i] & 0xFF;
            int v2 = d2[i] & 0xFF;
            if (v1 != v2) {
                comparison = Integer.compare(v1, v2);
                break;
            }
        }
        
        assertTrue(comparison < 0, "Binary representation of earlier UUID should be smaller");
    }
}
