package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import dev.pschmalz.wave_function_collapse.infrastructure.view.Scene;
import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class ImagesGridViewModel {
    private final List<PImage> images = new CopyOnWriteArrayList<>();
    private volatile int distanceBetween = 10;
    private volatile int distanceEdge = 10;
    private volatile int size = 30;
    private volatile int background = 255;

    public Stream<PImage> getImages() {
        return images.stream();
    }

    public void addImage(PImage image) {
        images.add(image);
    }

    public void clearImages() {
        images.clear();
    }

    public int getDistanceBetween() {
        return distanceBetween;
    }

    public void setDistanceBetween(int distanceBetween) {
        this.distanceBetween = distanceBetween;
    }

    public int getDistanceEdge() {
        return distanceEdge;
    }

    public void setDistanceEdge(int distanceEdge) {
        this.distanceEdge = distanceEdge;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public float getLowerEdge() {
        return 0;
    }

    public float getRightEdge() {
        return 0;
    }
}
