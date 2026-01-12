package com.mongodb.id.generator;

import java.util.UUID;

/**
 * Value object representing a User's unique identifier.
 * Used for RLS (Row Level Security) enforcement.
 */
public record UserId(UUID value) {
    public static UserId of(UUID value) {
        return new UserId(value);
    }
}
