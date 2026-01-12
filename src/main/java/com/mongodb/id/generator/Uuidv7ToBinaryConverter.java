package com.mongodb.id.generator;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Converts a UUID to a MongoDB Binary (Subtype 4).
 * Ensures big-endian byte order for time-ordered index efficiency.
 */
@WritingConverter
public class Uuidv7ToBinaryConverter implements Converter<UUID, Binary> {

    @Override
    public Binary convert(UUID source) {
        if (source == null) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(source.getMostSignificantBits());
        buffer.putLong(source.getLeastSignificantBits());
        return new Binary((byte) 4, buffer.array());
    }
}
