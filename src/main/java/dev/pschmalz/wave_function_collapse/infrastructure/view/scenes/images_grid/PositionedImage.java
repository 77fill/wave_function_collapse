package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import processing.core.PImage;
import processing.core.PVector;

import java.util.Optional;

public class PositionedImage {
    private final Optional<PVector> position;
    private final PImage image;
    private int width, height;

    public PositionedImage(Optional<PVector> position, PImage image) {
        this.image = image;
        this.position = position;
    }

    public boolean hasSpace() {
        return position.isPresent();
    }

    public PImage getImage() {
        return image;
    }

    public float getX() {
        return position.get().x;
    }

    public float getY() {
        return position.get().y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
