package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.infrastructure.view.DisplayExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {
    @Value("${multithreading.background.thread-count}")
    private Integer backgroundThreadCount;

    @Bean
    public Executor display() {
        return new DisplayExecutor();
    }

    @Bean
    public Executor background() {
        return Executors.newFixedThreadPool(backgroundThreadCount);
    }
}
