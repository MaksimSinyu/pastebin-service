package com.pastebin.service.metrics.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {

    private final Counter pasteCreationCounter;
    private final Counter pasteViewCounter;
    private final PrometheusMeterRegistry registry;

    @Autowired
    public MetricsController(Counter pasteCreationCounter, Counter pasteViewCounter, PrometheusMeterRegistry registry) {
        this.pasteCreationCounter = pasteCreationCounter;
        this.pasteViewCounter = pasteViewCounter;
        this.registry = registry;
    }

    @PostMapping("/paste-created")
    public void pasteCreated() {
        pasteCreationCounter.increment();
    }

    @PostMapping("/paste-viewed")
    public void pasteViewed() {
        pasteViewCounter.increment();
    }

    @GetMapping("/current-values")
    public String getCurrentValues() {
        return "Paste Created: " + pasteCreationCounter.count() +
                ", Paste Viewed: " + pasteViewCounter.count();
    }

    @GetMapping("/prometheus")
    public String prometheusMetrics() {
        return registry.scrape();
    }
}