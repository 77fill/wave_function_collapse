package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.usecase.data.Image;
import reactor.core.publisher.Mono;

import java.io.File;

public interface FileSystemStore {
    Mono<File> getTempDirectoryPath();
    Mono<dev.pschmalz.wave_function_collapse.domain.Image> getImage(File imagePath);
    void addImageToTempDirectory(Image image);
}
