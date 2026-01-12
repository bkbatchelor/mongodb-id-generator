package com.mongodb.id.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.UUID;

/**
 * Implements a Query Buffer strategy to account for potential clock skew
 * across different nodes when querying time-ordered UUIDv7 identifiers.
 */
public class QueryBuffer {
    private static final Logger log = LoggerFactory.getLogger(QueryBuffer.class);
    
    private final long maxSkewMillis;

    public QueryBuffer(long maxSkewMillis) {
        this.maxSkewMillis = maxSkewMillis;
    }

    public Instant adjustStart(Instant start) {
        Instant adjusted = start.minusMillis(maxSkewMillis);
        log.debug("Adjusting query start time from {} to {} (skew: {}ms)", start, adjusted, maxSkewMillis);
        return adjusted;
    }

    public Instant adjustEnd(Instant end) {
        Instant adjusted = end.plusMillis(maxSkewMillis);
        log.debug("Adjusting query end time from {} to {} (skew: {}ms)", end, adjusted, maxSkewMillis);
        return adjusted;
    }

    /**
     * Creates a UUID that represents the start of the search range for a given timestamp.
     * All bits after the 48-bit timestamp are set to zero.
     */
    public UUID getStartUuid(Instant timestamp) {
        long ts = adjustStart(timestamp).toEpochMilli();
        long msb = (ts & 0xFFFFFFFFFFFFL) << 16;
        // version 7 is not strictly required for range queries, but we use 0 entropy
        return new UUID(msb, 0L);
    }

    /**
     * Creates a UUID that represents the end of the search range for a given timestamp.
     * All bits after the 48-bit timestamp are set to one.
     */
    public UUID getEndUuid(Instant timestamp) {
        long ts = adjustEnd(timestamp).toEpochMilli();
        long msb = ((ts & 0xFFFFFFFFFFFFL) << 16) | 0xFFFFL;
        return new UUID(msb, -1L);
    }
}
