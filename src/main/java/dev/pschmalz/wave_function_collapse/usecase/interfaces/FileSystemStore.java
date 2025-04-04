package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import reactor.core.publisher.Mono;

import java.io.File;
import dev.pschmalz.wave_function_collapse.usecase.data.Image;

public interface FileSystemStore {
    Mono<File> getTempDirectoryPath();
    java.awt.Image getImage(File imagePath);
    void addImageToTempDirectory(Image image);
}
