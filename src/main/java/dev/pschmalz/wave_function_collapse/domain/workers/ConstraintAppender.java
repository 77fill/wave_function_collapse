package dev.pschmalz.wave_function_collapse.domain.workers;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.SmartConstraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static io.vavr.API.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConstraintAppender implements Function1<Stream<Tile>,Stream<Tile>> {
    ConstraintGenerator constraintGenerator;

    @Override
    public Stream<Tile> apply(Stream<Tile> tiles) {
        return tiles.zip(Stream(tiles).cycle())
                    .map(to_tile_otherTiles)
                    .flatMap(toStream_tile_otherTile)
                    .map(to_tile_constraints)
                    .map(to_tileWithConstraints);
    }

    private Tuple2<Tile, Stream<Tile>> to_tile_otherTiles(Tile tile, Stream<Tile> allTiles) {
        return Tuple(tile, allTiles.remove(tile));
    }

    private Stream<Tuple2<Tile,Tile>> toStream_tile_otherTile(Tile tile, Stream<Tile> otherTiles) {
        return Stream(tile).cycle().zip(otherTiles);
    }

    private Tuple2<Tile, Set<SmartConstraint>> to_tile_constraints(Tile tile, Tile otherTile) {
        return Tuple(tile, constraintGenerator.apply(tile,otherTile));
    }

    private Tile to_tileWithConstraint(Tile tile, Set<SmartConstraint> constraints) {
        return tile.withConstraints(constraints);
    }

    Function1<
            Tuple2<Tile, Stream<Tile>>,
            Tuple2<Tile, Stream<Tile>>> to_tile_otherTiles = Function(this::to_tile_otherTiles).tupled();
    Function1<
            Tuple2<Tile, Stream<Tile>>,
            Stream<Tuple2<Tile,Tile>>> toStream_tile_otherTile = Function(this::toStream_tile_otherTile).tupled();
    Function1<
            Tuple2<Tile, Tile>,
            Tuple2<Tile,Set<SmartConstraint>>> to_tile_constraints = Function(this::to_tile_constraints).tupled();
    Function1<
            Tuple2<Tile, Set<SmartConstraint>>,
            Tile> to_tileWithConstraints = Function(this::to_tileWithConstraint).tupled();

}
