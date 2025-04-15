package dev.pschmalz.wave_function_collapse.infrastructure.gui.util;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import processing.core.PImage;

public class ImageUtil {
    private ImageUtil() {}

    public static PImage toPImage(Image image) {
        var pImage = new PImage(image.getWidth(), image.getHeight());

        pImage.pixels = image.getPixels();

        return pImage;
    }
}
