package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import io.vavr.concurrent.Future;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import static io.vavr.API.Future;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InitTempDirectory implements Future<Void> {
    ClasspathStore classpathStore;
    FileSystemStore fileSystemStore;

    @NonFinal
    @Delegate
    Future<Void> future;

    private Void computation() {
        classpathStore.getExampleImages()
                .andThen(images -> images.forEach(fileSystemStore::addImageToTempDirectory));
        return null;
    };

    public void run() {
        future = Future(this::computation);
    }

    @Override
    public Future<Void> await() {
        run();

        return future.await();
    }
}
