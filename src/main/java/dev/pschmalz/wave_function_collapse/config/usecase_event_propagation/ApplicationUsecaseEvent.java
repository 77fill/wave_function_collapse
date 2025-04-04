package dev.pschmalz.wave_function_collapse.config.usecase_event_propagation;

import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import org.springframework.context.ApplicationEvent;

public class ApplicationUsecaseEvent extends ApplicationEvent {
    private final Usecase.Event usecaseEvent;

    public ApplicationUsecaseEvent(Usecase.Event usecaseEvent) {
        super(usecaseEvent.source());
        this.usecaseEvent = usecaseEvent;
    }

    public Usecase.Event getUsecaseEvent() {
        return usecaseEvent;
    }
}
