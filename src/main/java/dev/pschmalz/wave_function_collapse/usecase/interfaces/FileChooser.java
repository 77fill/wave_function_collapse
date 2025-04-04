package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.function.Function;

public interface FileChooser {
    Flux<File> chooseImagePaths(File startDirectory);
}
