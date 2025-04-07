package dev.pschmalz.wave_function_collapse.domain_workers.wfc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.BooleanSupplier;
import java.util.function.UnaryOperator;

@Component
public class TimeLoop <T> {
    private T something;
    private UnaryOperator<T> convertSomething;
    private BooleanSupplier whileSomething;
    private int maxRepeat;
    @Autowired
    private History history;

    public TimeLoop<T> apply(UnaryOperator<T> convertSomething) {
        this.convertSomething = convertSomething;
        return this;
    }

    public TimeLoop<T> on(T something) {
        this.something = something;
        return this;
    }

    public TimeLoop<T> while_(BooleanSupplier whileSomething) {
        this.whileSomething = whileSomething;
        return this;
    }

    public TimeLoop<T> maxRepeat(int maxRepeat) {
        this.maxRepeat = maxRepeat;
        return this;
    }

    public TimeLoop<T> start() {
        int currentRepetition = 0;
        T old = something;

        do {
            something = old;
            something = convertSomething.apply(something);
        }
        while (whileSomething.getAsBoolean()
                && currentRepetition++ < maxRepeat);

        if(currentRepetition >= maxRepeat)
            throw new IllegalStateException();

        return this;
    }

    public T getResult() {
        return something;
    }
}
