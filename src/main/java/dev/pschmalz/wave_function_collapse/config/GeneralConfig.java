package dev.pschmalz.wave_function_collapse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class GeneralConfig {
    @Bean
    public Random random() {
        return new Random();
    }
}
