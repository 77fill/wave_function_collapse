package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import lombok.Value;

import java.io.File;

@Value
public class Image {
    int width;
    int height;
    File file;
}
