package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.MemoryGridStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import reactor.core.Disposable;

import java.util.function.Consumer;

public class ShowTileSlotGrid extends Usecase {
    private final View view;
    private final MemoryGridStore gridStore;

    @Override
    protected void handleRun() {
        disposable =
        gridStore
                .get()
                .subscribe(
                        view::showGrid,
                        this::onException,
                        this::onSuccess
                );
    }

    @Override
    protected void handleCancel() {
        disposable.dispose();
    }

    private Disposable disposable;

    public ShowTileSlotGrid(Consumer<Event> eventEmitter, View view, MemoryGridStore gridStore) {
        super(eventEmitter);
        this.view = view;
        this.gridStore = gridStore;
    }
}
