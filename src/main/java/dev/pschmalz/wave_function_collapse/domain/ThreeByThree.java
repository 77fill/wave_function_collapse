package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class ThreeByThree implements ConstraintGenerator {
    @Override
    public Set<SmartConstraint> apply(Tile tile, Tile otherTile) {
        return new ThreeByThreeSpecific(tile, otherTile).getConstraints();
    }
}

class ThreeByThreeSpecific {
    private final Tile tile, otherTile;

    ThreeByThreeSpecific(Tile tile, Tile otherTile) {
        this.tile = tile;
        this.otherTile = otherTile;
    }

    public Set<SmartConstraint> getConstraints() {
        return Edge.all()
                .map(this::toEdgeAndPermission)
                .map(this::toSmartConstraint)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }


    private Tuple2<Edge, Boolean> toEdgeAndPermission(Edge edge) {
        return Tuples.of(
                edge,
                Arrays.equals(
                        edge.of(tile.getImage()),
                        edge.opposite.of(otherTile.getImage())));
    }

    private Optional<SmartConstraint> toSmartConstraint(Tuple2<Edge, Boolean> edgeAndPermission) {
        if(edgeAndPermission.getT2().equals(true))
            return Optional.empty();

        var edge = edgeAndPermission.getT1();

        Constraint constraint = tile -> !tile.equals(otherTile);
        return Optional.of(tileSlot -> Tuples.of(tileSlot.getNeighbor(edge.direction), constraint));
    }
}

enum Edge {
    UP, DOWN, LEFT, RIGHT;

    Edge opposite;
    TileSlot.Direction direction;

    static {
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;

        UP.direction = TileSlot.Direction.UP;
        DOWN.direction = TileSlot.Direction.DOWN;
        LEFT.direction = TileSlot.Direction.LEFT;
        RIGHT.direction = TileSlot.Direction.RIGHT;
    }

    static Stream<Edge> all() {
        return Arrays.stream(values());
    }

    Pixel[] of(Image image) {
        Stream<Pixel> p;
        var width = image.getWidth();
        var height = image.getHeight();
        var maxX = width-1;
        var maxY = height-1;

        switch (this) {
            case UP -> p = range(0, width).mapToObj(x -> image.get(x, 0));
            case DOWN -> p = range(0, width).mapToObj(x -> image.get(x, maxY));
            case LEFT -> p = range(0, height).mapToObj(y -> image.get(0, y));
            case RIGHT -> p = range(0, height).mapToObj(y -> image.get(maxX, y));
            default -> throw new IllegalStateException();
        }

        return p.toArray(Pixel[]::new);
    }
}