package dev.pschmalz.wave_function_collapse.domain.workers;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.*;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;

import static io.vavr.API.*;
import static io.vavr.collection.Stream.range;

public class ThreeByThree implements ConstraintGenerator {
    @Override
    public Set<SmartConstraint> apply(Tile tile, Tile otherTile) {
        return Edge.all()
                .map(toPermission.apply(tile,otherTile))
                .zip(Edge.all())
                .filter(permission_edge -> !permission_edge._1)
                .map(Tuple2::_2)
                .map(toSmartConstraint.apply(otherTile))
                .toSet();
    }

    private final Function3<Tile,Tile,Edge,Boolean> toPermission = Function(this::toPermission);
    private boolean toPermission(Tile tile, Tile otherTile, Edge edge) {
        return edge.of(tile.getImage())
                .equals(
                        edge.opposite.of(otherTile.getImage()));
    }

    private final Function2<Tile,Edge,SmartConstraint> toSmartConstraint = Function(this::toSmartConstraint);
    private SmartConstraint toSmartConstraint(Tile forbiddenTile, Edge edge) {
        return (grid, sourceTileSlot) -> Tuple(
                edge.direction.getNeighbor(grid, sourceTileSlot),
                Constraint.forbidden(forbiddenTile));
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
        return Stream.of(values());
    }

    Array<Pixel> of(Image image) {
        var width = image.getWidth();
        var height = image.getHeight();
        var maxX = width-1;
        var maxY = height-1;

        return Match(this).of(
                Case($(UP), range(0, width).map(x -> image.get(x, 0))),
                Case($(DOWN), range(0, width).map(x -> image.get(x, maxY))),
                Case($(LEFT), range(0, height).map(y -> image.get(0, y))),
                Case($(RIGHT), range(0, height).map(y -> image.get(maxX, y)))
        ).toArray();

    }
}