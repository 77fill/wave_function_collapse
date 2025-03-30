package dev.pschmalz.wave_function_collapse.domain.wfc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BooleanSupplier;

@Component
public class TimeLoop {
    private Runnable doSomething;
    private BooleanSupplier whileSomething;
    private int maxRepeat;
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

    public TimeLoop maxRepeat(int maxRepeat) {
        this.maxRepeat = maxRepeat;
        return this;
    }

    public void start() {
        int currentRepetition = 0;

        do {
            history.restoreIfExists();
            doSomething.run();
        }
        while (whileSomething.getAsBoolean()
                && currentRepetition++ < maxRepeat);

        if(currentRepetition >= maxRepeat)
            throw new IllegalStateException();

        history.update();
    }
}
