package dev.pschmalz.wave_function_collapse.domain;

import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public interface Constraint extends Predicate<Tile> {

    static Constraint allowedOnly(Collection<Tile> allowedTiles) {
        return allowedTiles::contains;
    }

    static BinaryOperator<Constraint> and = (a, b) -> (tile -> a.and(b).test(tile));
}
