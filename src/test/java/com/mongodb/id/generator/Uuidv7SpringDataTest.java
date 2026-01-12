package com.mongodb.id.generator;

import org.bson.types.Binary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Uuidv7SpringDataTest {

    @Test
    @DisplayName("Spring Data should use custom converters for UUID to Binary conversion")
    void springDataShouldUseCustomConverters() {
        // Setup MongoCustomConversions with our converters
        MongoCustomConversions conversions = new MongoCustomConversions(Arrays.asList(
            new Uuidv7ToBinaryConverter(),
            new BinaryToUuidv7Converter()
        ));

        MongoMappingContext context = new MongoMappingContext();
        MappingMongoConverter converter = new MappingMongoConverter(NoOpDbRefResolver.INSTANCE, context);
        converter.setCustomConversions(conversions);
        converter.afterPropertiesSet();

        UUID uuid = UUID.randomUUID();
        
        // Test Writing
        Object converted = converter.convertToMongoType(uuid);
        assertThat(converted).isInstanceOf(Binary.class);
        Binary binary = (Binary) converted;
        assertThat(binary.getType()).isEqualTo((byte) 4);

        // Test Reading via the conversion service which MappingMongoConverter uses
        UUID read = converter.getConversionService().convert(binary, UUID.class);
        assertThat(read).isEqualTo(uuid);
    }
}
