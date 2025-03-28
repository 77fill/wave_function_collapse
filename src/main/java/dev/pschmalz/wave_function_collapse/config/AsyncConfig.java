package dev.pschmalz.wave_function_collapse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {
    @Bean
    public Executor display() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    public Executor background() {
        return Executors.newFixedThreadPool(3);
    }
}
