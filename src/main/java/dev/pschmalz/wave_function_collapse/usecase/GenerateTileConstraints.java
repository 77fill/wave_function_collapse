package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.ConstraintAppender;
import dev.pschmalz.wave_function_collapse.domain.MemoryTileStore;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import reactor.core.Disposable;

import java.util.function.Consumer;

public class GenerateTileConstraints extends Usecase {
    private final MemoryTileStore tileStore;
    private final ConstraintAppender constraintAppender;

    @Override
    protected void handleRun() {
        disposable =
        tileStore
                .getTiles()
                .transform(constraintAppender)
                .subscribeWith(
                        tileStore.addTiles(this::onException, this::onSuccess)
                );
    }

    @Override
    protected void handleCancel() {
        disposable.dispose();
    }

    private Disposable disposable = () -> {};

    public GenerateTileConstraints(Consumer<Event> eventEmitter, MemoryTileStore tileStore, ConstraintAppender constraintAppender) {
        super(eventEmitter);
        this.tileStore = tileStore;
        this.constraintAppender = constraintAppender;
    }
}
