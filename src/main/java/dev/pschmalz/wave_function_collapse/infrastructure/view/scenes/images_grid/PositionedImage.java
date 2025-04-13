package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import io.vavr.control.Option;
import lombok.Value;
import processing.core.PImage;
import processing.core.PVector;

@Value
public class PositionedImage {
    Option<PVector> position;
    PImage image;

    public boolean hasSpace() {
        return position.isDefined();
    }

    public float getX() {
        return position.get().x;
    }

    public float getY() {
        return position.get().y;
    }
}
