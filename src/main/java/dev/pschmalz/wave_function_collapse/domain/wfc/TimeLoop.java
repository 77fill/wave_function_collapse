package dev.pschmalz.wave_function_collapse.domain.wfc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BooleanSupplier;

@Component
public class TimeLoop {
    private Runnable doSomething;
    private BooleanSupplier whileSomething;
    @Autowired
    private History history;

    public TimeLoop do_(Runnable doSomething) {
        this.doSomething = doSomething;
        return this;
    }

    public TimeLoop while_(BooleanSupplier whileSomething) {
        this.whileSomething = whileSomething;
        return this;
    }

    public void start() {
        do {
            history.restoreIfExists();
            doSomething.run();
        }
        while (whileSomething.getAsBoolean());
        history.update();
    }
}
