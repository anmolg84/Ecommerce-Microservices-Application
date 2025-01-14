package com.anmol.order_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @LoadBalanced // Load Balancer For Multiple Instances
    @Bean // Crate WebClient Bean Inventory Service Communication
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}
