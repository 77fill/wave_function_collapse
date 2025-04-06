package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstraintAppender implements Function<Flux<Tile>,Flux<Tile>> {
    private ConstraintGenerator constraintGenerator;

    @Override
    public Flux<Tile> apply(Flux<Tile> tileFlux) {
        return toPairsOf_Tile_SetOfOtherTiles(tileFlux)
                .map(this::pairsOf_Tile_FoundConstraints)
                .map(this::toTileContainingConstraints);
    }

    private Flux<Tuple2<Tile,Set<Tile>>> toPairsOf_Tile_SetOfOtherTiles(Flux<Tile> tiles) {
        return Flux.zip(
                tiles.share(),
                tiles.collectList()
                        .map(HashSet::new)
                        .repeat());
    }

    private Tile toTileContainingConstraints(Tuple2<Tile, Set<SmartConstraint>> pair) {
        return new Tile(
                pair.getT1().getImage(),
                pair.getT2()
        );
    }

    private Tuple2<Tile, Set<SmartConstraint>> pairsOf_Tile_FoundConstraints(Tuple2<Tile, Set<Tile>> pair) {
        var tile = pair.getT1();
        var otherTiles = new HashSet<>(pair.getT2());
        otherTiles.remove(tile);
        var constraints =
                otherTiles.stream()
                    .flatMap(otherTile -> findConstraintsFor(tile, otherTile))
                    .collect(Collectors.toSet());

        return Tuples.of(tile, constraints);
    }

    private Stream<SmartConstraint> findConstraintsFor(Tile tile, Tile otherTile) {
        return constraintGenerator.apply(tile, otherTile).stream();
    }

    public ConstraintAppender(ConstraintGenerator constraintGenerator) {
        this.constraintGenerator = constraintGenerator;
    }
}
