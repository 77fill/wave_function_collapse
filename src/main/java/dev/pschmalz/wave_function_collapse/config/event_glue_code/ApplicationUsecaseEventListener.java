package dev.pschmalz.wave_function_collapse.config.event_glue_code;

import dev.pschmalz.wave_function_collapse.usecase.stereotypes.Usecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @see dev.pschmalz.wave_function_collapse.config.event_glue_code Solution -> Configuration -> 2. Glue code
 */
@Component
public class ApplicationUsecaseEventListener implements ApplicationListener<ApplicationUsecaseEvent> {
    @Autowired
    private List<Usecase.EventListener> usecaseEventListeners;

    @Override
    public void onApplicationEvent(ApplicationUsecaseEvent applicationUsecaseEvent) {
        usecaseEventListeners.forEach(
                listener -> listener.handleUsecaseEvent(
                        applicationUsecaseEvent.getUsecaseEvent()));
    }
}
