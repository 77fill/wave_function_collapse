package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import reactor.core.publisher.Flux;

import java.io.File;

public interface FileChooser {
    Flux<File> chooseImagePaths(File startDirectory);
}
