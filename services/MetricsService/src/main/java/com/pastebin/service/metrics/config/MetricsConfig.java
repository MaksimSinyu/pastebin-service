package com.pastebin.service.metrics.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "metrics-service");
    }

    @Bean
    public Counter pasteCreationCounter(PrometheusMeterRegistry registry) {
        return Counter.builder("paste_creations_total")
                .description("Total number of pastes created")
                .register(registry);
    }

    @Bean
    public Counter pasteViewCounter(PrometheusMeterRegistry registry) {
        return Counter.builder("paste_views_total")
                .description("Total number of pastes viewed")
                .register(registry);
    }
}