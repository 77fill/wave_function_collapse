package dev.pschmalz.wave_function_collapse.config.event_glue_code;

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

    /**
     * @see dev.pschmalz.wave_function_collapse.config.event_glue_code Solution -> Usecase Layer -> 'custom'
     */
    @Bean
    public Consumer<Usecase.Event> usecaseEventEmitter() {
        return usecaseEvent ->
                publisher.publishEvent(
                        new ApplicationUsecaseEvent(usecaseEvent));
    }
}

