package dev.pschmalz.wave_function_collapse.usecase.sterotypes;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Usecase implements Runnable {
    private volatile State state = State.IDLE;
    private volatile Optional<Throwable> throwable = Optional.empty();
    private final Consumer<Event> eventEmitter;

    public Usecase(Consumer<Event> eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    abstract protected void handleCancel();
    abstract protected void handleRun();

    private void setState(State state) {
        this.state = state;
        eventEmitter.accept(new Event(this,state));
    }

    final public void cancel() {
        handleCancel();
        setState(State.CANCELLED);
    }

    @Override
    final public void run() {
        setState(State.RUNNING);
        handleRun();
    }

    final protected void onException(Throwable throwable) {
        setState(State.FAILED);
        this.throwable = Optional.of(throwable);
    }

    final protected void onSuccess() {
        setState(State.SUCCESS);
    }

    final public boolean isRunning() {
        return state == State.RUNNING;
    }
    final public boolean isDone() {
        return state.isDone();
    }
    final public boolean isIdle() {
        return state == State.IDLE;
    }
    final public boolean hasFailed() {
        return state == State.FAILED;
    }
    final public Optional<Throwable> getThrowable() {
        return throwable;
    }

    public enum State {
        IDLE,
        RUNNING,
        CANCELLED, SUCCESS, FAILED;

        private static final List<State> doneStates = List.of(CANCELLED, SUCCESS, FAILED);

        private boolean isDone() {
            return doneStates.contains(this);
        }
    }

    public record Event(Usecase source, State state) {}
}
