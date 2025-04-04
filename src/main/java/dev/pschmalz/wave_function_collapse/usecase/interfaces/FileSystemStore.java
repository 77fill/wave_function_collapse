package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import reactor.core.publisher.Mono;

import java.awt.*;
import java.io.File;

public interface FileSystemStore {
    Mono<File> getTempDirectoryPath();
    Image getImage(File imagePath);
}
