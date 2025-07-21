package org.example;

import org.example.config.LimitsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(LimitsProperties.class)
public class LimitsApp {
    public static void main(String[] args) {
        SpringApplication.run(LimitsApp.class, args);
    }
}