package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import java.util.Collection;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public interface Constraint extends Predicate<Tile> {

    default boolean isFulfilledBy(Tile target) {
        return this.test(target);
    }

    static Constraint allowingOnly(Collection<Tile> allowedTiles) {
        return allowedTiles::contains;
    }

    BinaryOperator<Constraint> and = (a, b) ->  a.and(b)::test;
    BinaryOperator<Constraint> or = (a, b) -> a.or(b)::test;
}
