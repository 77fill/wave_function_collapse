package dev.pschmalz.wave_function_collapse.config.usecase_event_propagation;

import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

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
