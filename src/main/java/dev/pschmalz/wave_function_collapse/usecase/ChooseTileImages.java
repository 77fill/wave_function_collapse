package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import io.vavr.collection.Set;
import io.vavr.concurrent.Future;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import static io.vavr.API.Future;
import static io.vavr.API.Stream;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ChooseTileImages implements Future<Set<Tile>> {
    FileSystemStore fileSystemStore;
    FileChooser fileChooser;

    @NonFinal
    @Delegate
    Future<Set<Tile>> future;

    private Set<Tile> computation() {
        return Stream(fileSystemStore.getTempDirectoryPath())
                .flatMap(fileChooser::chooseImagePaths)
                .map(fileSystemStore::getImage)
                .map(Image::toTile)
                .toSet();
    }

    public void run() {
        future = Future(this::computation);
    }
}
