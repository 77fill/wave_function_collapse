package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.MemoryTileStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import reactor.core.Disposable;

import java.util.function.Consumer;

public class ShowTileImages extends Usecase {
    private final MemoryTileStore tileStore;
    private final View view;

    @Override
    protected void handleRun() {
        disposable =
        tileStore
                .getImages()
                .subscribe(
                        view::showImage,
                        this::onException,
                        this::onSuccess
                );
    }

    @Override
    protected void handleCancel() {
        disposable.dispose();
    }

    private Disposable disposable = () -> {};

    public ShowTileImages(Consumer<Event> eventEmitter, MemoryTileStore tileStore, View view) {
        super(eventEmitter);
        this.tileStore = tileStore;
        this.view = view;
    }
}
