package dev.pschmalz.wave_function_collapse.model;

import processing.core.PImage;

public class Tile {
    private PImage image;

    public Tile(PImage image) {
        this.image = image;
    }

    public PImage getImage() {
        return image;
    }

    public void setImage(PImage image) {
        this.image = image;
    }
}
