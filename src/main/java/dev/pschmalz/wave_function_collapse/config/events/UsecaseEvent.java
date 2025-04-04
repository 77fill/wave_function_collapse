package dev.pschmalz.wave_function_collapse.config.events;

import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import org.springframework.context.ApplicationEvent;

public class UsecaseEvent extends ApplicationEvent {
    private final Usecase.State usecaseState;

    public UsecaseEvent(Usecase source, Usecase.State usecaseState) {
        super(source);
        this.usecaseState = usecaseState;
    }

    public Usecase.State getUsecaseState() {
        return usecaseState;
    }
}
