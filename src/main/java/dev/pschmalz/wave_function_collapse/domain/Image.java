package dev.pschmalz.wave_function_collapse.domain;

import java.awt.image.BufferedImage;

public class Image {
    private final BufferedImage bufferedImage;

    public Image(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public Pixel get(int x, int y) {
        return new Pixel(0,0,0);
    }
}
