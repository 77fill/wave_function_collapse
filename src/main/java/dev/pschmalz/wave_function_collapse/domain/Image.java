package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import io.vavr.collection.HashSet;

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
        var p = bufferedImage.getRGB(x,y);
        return new Pixel(p >> 16, (p >> 8) & 0xff, p & 0xff);
    }

    public int[] getPixels() {
        return bufferedImage.getRGB(0,0,getWidth(),getHeight(),null,0,getWidth());
    }

    public Tile toTile() {
        return new Tile(this, HashSet.empty());
    }
}
