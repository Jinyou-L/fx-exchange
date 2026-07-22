package com.fx.api.feed;

import com.fx.api.repo.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeedService {
    private static final Logger log = LoggerFactory.getLogger(FeedService.class);

    private final RateRepository rates;
    private final AcceptingState accepting;
    private final OrchestratorClient orchestrator;

    public FeedService(RateRepository rates, AcceptingState accepting, OrchestratorClient orchestrator) {
        this.rates = rates;
        this.accepting = accepting;
        this.orchestrator = orchestrator;
    }

    public void handle(IncomingBatch batch) {
        boolean isAccepting = accepting.isAccepting();
        String status = isAccepting ? "ACCEPTED" : "DECLINED";
        if (isAccepting) {
            batch.rates().forEach(rate -> rates.insert(rate.base(), rate.quote(), rate.rate()));
            log.info("Stored {} rates from batch {}", batch.rates().size(), batch.batchId());
        } else {
            log.info("Declined batch {} while accepting is disabled", batch.batchId());
        }
        orchestrator.acknowledge(batch.batchId(), status);
    }
}
