package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import reactor.core.Disposable;

import java.util.function.Consumer;

public class InitTempDirectory extends Usecase {
    private final ClasspathStore classpathStore;
    private final FileSystemStore fileSystemStore;

    @Override
    protected void handleRun() {
        disposable =
        classpathStore
                .getExampleImages()
                .subscribe(
                        fileSystemStore::addImageToTempDirectory,
                        this::onException,
                        this::onSuccess
                );
    }

    @Override
    protected void handleCancel() {
        disposable.dispose();
    }

    Disposable disposable = () -> {};

    public InitTempDirectory(Consumer<Event> eventEmitter, ClasspathStore classpathStore, FileSystemStore fileSystemStore) {
        super(eventEmitter);
        this.classpathStore = classpathStore;
        this.fileSystemStore = fileSystemStore;
    }
}
