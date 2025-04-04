package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.domain.Image;

import java.io.File;
import java.util.stream.Stream;

public interface View {
    void tempDirectoryLoaded();

    void tilesLoaded();

    void clear();

    void showImages(Stream<File> images);

    void restraintsGenerated();

    void finishedWFC();

    void showImage(Image image);
}
