package dev.pschmalz.wave_function_collapse.infrastructure.view;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import dev.pschmalz.wave_function_collapse.infrastructure.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class ImagesView implements SubView {
    private int distanceBetween = 10;
    private int distanceEdge = 10;
    private int size = 30;

    @Autowired
    private Util util;

    @Autowired
    private PApplet p;

    private List<PImage> images = List.of();

    @Override
    public void show() {
        p.background(0);

        Streams.zip(positions(), images.stream(), PositionedImage::new)
                .filter(this::hasSpace)
                .forEachOrdered(
                        item -> p.image(
                                    item.image(),
                                    item.pos().get().x,
                                    item.pos().get().y,
                                    size,
                                    size));
    }

    private boolean hasSpace(PositionedImage posImg) {
        return posImg.pos().isPresent();
    }

    private Stream<Optional<PVector>> positions() {
        return Stream.iterate(
                    firstPoint(),
                    point -> isRightmost(point)?
                                toTheNextRow(point)
                                : toTheRightOf(point))
                .map(point -> overLowerEdge(point)?
                                null
                                :point)
                .map(Optional::ofNullable);
    }

    private record PositionedImage(Optional<PVector> pos, PImage image) {}

    private boolean overLowerEdge(PVector point) {
        return lowerEdge() < point.y + size;
    }

    private int lowerEdge() {
        return p.height - distanceEdge;
    }

    private PVector toTheNextRow(PVector point) {
        return new PVector(distanceEdge, point.y + size + distanceBetween);
    }

    private PVector toTheRightOf(PVector point) {
        return new PVector(point.x + size + distanceBetween, point.y);
    }

    private PVector firstPoint() {
        return new PVector(distanceEdge, distanceEdge);
    }

    private boolean isRightmost(PVector point) {
        return rightEdge() < point.x + size;
    }

    private int rightEdge() {
        return p.width - distanceEdge;
    }

    public void clear() {
        images = List.of();
    }

    public void set(Stream<PImage> images) {
        this.images = ImmutableList.copyOf(images.toList());
    }

    public void setDistanceBetween(int distance) {
        distanceBetween = distance;
    }
}
