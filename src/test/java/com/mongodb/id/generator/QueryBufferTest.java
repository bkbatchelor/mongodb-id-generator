package com.mongodb.id.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QueryBufferTest {

    private final QueryBuffer queryBuffer = new QueryBuffer(5000); // 5s window

    @Test
    @DisplayName("Should adjust start time backwards by max skew")
    void shouldAdjustStartTime() {
        Instant now = Instant.now();
        Instant adjusted = queryBuffer.adjustStart(now);
        
        assertEquals(now.minusMillis(5000), adjusted);
    }

    @Test
    @DisplayName("Should adjust end time forwards by max skew")
    void shouldAdjustEndTime() {
        Instant now = Instant.now();
        Instant adjusted = queryBuffer.adjustEnd(now);
        
        assertEquals(now.plusMillis(5000), adjusted);
    }

    @Test
    @DisplayName("Should create UUID range for adjusted window")
    void shouldCreateUuidRange() {
        Instant now = Instant.now();
        
        UUID start = queryBuffer.getStartUuid(now);
        UUID end = queryBuffer.getEndUuid(now);
        
        long startTs = start.getMostSignificantBits() >>> 16;
        long endTs = end.getMostSignificantBits() >>> 16;
        
        assertEquals(now.minusMillis(5000).toEpochMilli(), startTs);
        assertEquals(now.plusMillis(5000).toEpochMilli(), endTs);
        
        // Start should have all zeros in entropy
        assertEquals(0L, start.getLeastSignificantBits());
        // End should have all ones in entropy
        assertEquals(-1L, end.getLeastSignificantBits());
    }
}
