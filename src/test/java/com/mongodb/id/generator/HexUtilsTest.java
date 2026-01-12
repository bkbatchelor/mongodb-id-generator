package com.mongodb.id.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HexUtilsTest {

    @Test
    @DisplayName("Should encode UUID to 32-character hex string")
    void shouldEncodeUuidToHex() {
        UUID uuid = UUID.fromString("018d1594-e69f-7000-8123-456789abcdef");
        String hex = HexUtils.encode(uuid);
        assertEquals("018d1594e69f70008123456789abcdef", hex);
    }

    @Test
    @DisplayName("Should decode 32-character hex string to UUID")
    void shouldDecodeHexToUuid() {
        String hex = "018d1594e69f70008123456789abcdef";
        UUID uuid = HexUtils.decode(hex);
        assertEquals("018d1594-e69f-7000-8123-456789abcdef", uuid.toString());
    }

    @Test
    @DisplayName("Should be reversible")
    void shouldBeReversible() {
        UUID original = UUID.randomUUID();
        String hex = HexUtils.encode(original);
        UUID decoded = HexUtils.decode(hex);
        assertEquals(original, decoded);
    }

    @Test
    @DisplayName("Should throw exception for invalid hex length")
    void shouldThrowOnInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> HexUtils.decode("abc"));
    }
}
