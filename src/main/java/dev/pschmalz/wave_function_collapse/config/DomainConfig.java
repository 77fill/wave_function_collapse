package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.domain.workers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class DomainConfig {
    @Autowired
    private Random random;

    @Bean
    public ConstraintAppender constraintAppender() {
        return new ConstraintAppender(defaultConstraintGenerator());
    }

    @Bean
    public ConstraintGenerator defaultConstraintGenerator() {
        return new ThreeByThree();
    }

    @Bean
    public WaveFunctionCollapse constraintApplicationCascade() {
        return new WaveFunctionCollapse();
    }

    @Bean
    public TileSlotGridGenerator tileSlotGridGenerator() {
        return new TileSlotGridGenerator(random);
    }
}
