package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;


public class ShowTileImages {
    MemoryTileStore tileStore;
    View view;

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

}
