package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import io.vavr.collection.Stream;

import java.io.File;

public interface FileChooser {
    Stream<File> chooseImagePaths(File startDirectory);
}
