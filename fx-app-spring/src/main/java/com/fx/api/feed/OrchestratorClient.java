package com.fx.api.feed;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrchestratorClient {
    private static final Logger log = LoggerFactory.getLogger(OrchestratorClient.class);

    private final RestTemplate http;
    private final String baseUrl;

    public OrchestratorClient(RestTemplate http,
                              @Value("${fx.orchestrator.url}") String baseUrl) {
        this.http = http;
        this.baseUrl = baseUrl;
    }

    /** A callback failure is logged, never allowed to fail the inbound request. */
    public void acknowledge(long batchId, String status) {
        try {
            http.postForLocation(baseUrl + "/api/feed/ack",
                    Map.of("batchId", batchId, "status", status));
        } catch (Exception ex) {
            log.warn("Could not acknowledge batch {} as {}", batchId, status, ex);
        }
    }
}
