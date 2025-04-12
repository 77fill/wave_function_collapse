package dev.pschmalz.wave_function_collapse.config.event_glue_code;

import dev.pschmalz.wave_function_collapse.usecase.stereotypes.Usecase;
import org.springframework.context.ApplicationEvent;

/**
 * Look at <pre>@see</pre>: ApplicationEventPublisher needs an ApplicationEvent ... this is it
 * @see dev.pschmalz.wave_function_collapse.config.event_glue_code Solution -> Configuration -> 1. Glue code
 */
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
