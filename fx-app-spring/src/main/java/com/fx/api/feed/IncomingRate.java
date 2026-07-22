package com.fx.api.feed;

/** One rate received from the upstream orchestrator. */
public record IncomingRate(String base, String quote, double rate) {}
