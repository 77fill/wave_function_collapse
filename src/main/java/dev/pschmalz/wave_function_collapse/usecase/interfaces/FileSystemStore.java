package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.usecase.data.Image;

import java.io.File;

public interface FileSystemStore {
    File getTempDirectoryPath();
    dev.pschmalz.wave_function_collapse.domain.Image getImage(File imagePath);
    void addImageToTempDirectory(Image image);
}
