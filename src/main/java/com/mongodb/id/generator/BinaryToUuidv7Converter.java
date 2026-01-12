package com.mongodb.id.generator;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Converts a MongoDB Binary back to a UUID.
 */
@ReadingConverter
public class BinaryToUuidv7Converter implements Converter<Binary, UUID> {

    @Override
    public UUID convert(Binary source) {
        if (source == null) {
            return null;
        }
        byte[] data = source.getData();
        if (data.length != 16) {
            throw new IllegalArgumentException("Binary data must be 16 bytes for UUID");
        }
        ByteBuffer buffer = ByteBuffer.wrap(data);
        long mostSigBits = buffer.getLong();
        long leastSigBits = buffer.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }
}
