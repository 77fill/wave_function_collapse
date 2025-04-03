package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import java.io.File;

public class Image {
    private int width;
    private int height;
    private File file;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public File getFile() {
        return file;
    }

    public Image(File file) {
        this.file = file;
    }
}
