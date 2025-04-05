package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.MemoryTileStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import reactor.core.Disposable;

import java.util.function.Consumer;

public class LoadChosenTileImages extends Usecase {
    private final FileSystemStore fileSystemStore;
    private final FileChooser fileChooser;
    private final MemoryTileStore tileStore;

    @Override
    public void handleRun() {
        disposable =
        fileSystemStore
                .getTempDirectoryPath()
                .flatMapMany(fileChooser::chooseImagePaths)
                .flatMap(fileSystemStore::getImage)
                .subscribe(
                        tileStore::createTileFromImage,
                        this::onException,
                        this::onSuccess);
    }

    @Override
    protected void handleCancel() {
        disposable.dispose();
    }

    private Disposable disposable = () -> {};

    public LoadChosenTileImages(Consumer<Event> eventEmitter, FileSystemStore fileSystemStore, FileChooser fileChooser, MemoryTileStore tileStore) {
        super(eventEmitter);
        this.fileSystemStore = fileSystemStore;
        this.fileChooser = fileChooser;
        this.tileStore = tileStore;
    }
}
