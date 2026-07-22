package com.fx.api.feed;

import java.time.Instant;
import java.util.List;

/** Contract for POST /api/feed/rates. Field names match the upstream JSON exactly. */
public record IncomingBatch(long batchId, Instant generatedAt, List<IncomingRate> rates) {}
