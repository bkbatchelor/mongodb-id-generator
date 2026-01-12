package com.mongodb.id.generator;

import java.util.UUID;
import java.nio.ByteBuffer;

/**
 * Utilities for encoding and decoding UUIDs to/from Hexadecimal (Base16) strings.
 */
public final class HexUtils {

    private HexUtils() {
        // Utility class
    }

    /**
     * Encodes a UUID to a 32-character hexadecimal string.
     *
     * @param uuid the UUID to encode
     * @return a 32-character hex string
     */
    public static String encode(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return String.format("%016x%016x", uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }

    /**
     * Decodes a 32-character hexadecimal string to a UUID.
     *
     * @param hex the hex string to decode
     * @return the decoded UUID
     * @throws IllegalArgumentException if the hex string is not 32 characters long
     */
    public static UUID decode(String hex) {
        if (hex == null) {
            return null;
        }
        if (hex.length() != 32) {
            throw new IllegalArgumentException("Hex string must be exactly 32 characters long");
        }

        long mostSigBits = Long.parseUnsignedLong(hex.substring(0, 16), 16);
        long leastSigBits = Long.parseUnsignedLong(hex.substring(16, 32), 16);

        return new UUID(mostSigBits, leastSigBits);
    }
}
