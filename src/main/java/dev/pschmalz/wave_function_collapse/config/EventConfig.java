package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.config.usecase_event_propagation.ApplicationUsecaseEvent;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConfig {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Bean
    public Consumer<Usecase.Event> usecaseEventEmitter() {
        return usecaseEvent ->
                publisher.publishEvent(
                        new ApplicationUsecaseEvent(usecaseEvent));
    }
}

