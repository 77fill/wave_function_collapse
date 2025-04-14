package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import processing.core.PImage;
import processing.core.PVector;


@AllArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ImagesGridViewModel {
    volatile List<PImage> images = List.empty();
    volatile int distanceBetween, distanceEdge, size, background, width, height;
    volatile PVector upperLeft;

    public float getLowerEdge() {
        return upperLeft.y + height - distanceEdge;
    }

    public float getRightEdge() {
        return upperLeft.x + width - distanceEdge;
    }
}
