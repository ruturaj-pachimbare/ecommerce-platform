package com.ecommerce.notification_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaPropertyChecker {

    @Value("${spring.kafka.bootstrap-servers:NOT_FOUND}")
    private String bootstrapServers;

    @PostConstruct
    public void init() {
        System.out.println("BOOTSTRAP = " + bootstrapServers);
    }
}