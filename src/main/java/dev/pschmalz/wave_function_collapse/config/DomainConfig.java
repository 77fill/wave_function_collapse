package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public ConstraintAppender constraintAppender() {
        return new ConstraintAppender();
    }

    @Bean
    public ConstraintApplicationCascade constraintApplicationCascade() {
        return new ConstraintApplicationCascade();
    }

    @Bean
    public MemoryGridStore gridStore() {
        return new MemoryGridStore();
    }

    @Bean
    public MemoryTileStore tileStore() {
        return new MemoryTileStore();
    }

    @Bean
    public TileSlotGridGenerator tileSlotGridGenerator() {
        return new TileSlotGridGenerator();
    }
}
