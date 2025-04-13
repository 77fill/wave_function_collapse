package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.Set;
import io.vavr.control.Option;

import static io.vavr.API.Tuple;

public interface SmartConstraint extends Function2<TileSlotGrid, TileSlot, Tuple2<Option<TileSlot>, Constraint>> {

    static SmartConstraint or(Set<SmartConstraint> smartConstraints) {
        return (grid, source) ->
                Tuple(
                        smartConstraints.get().apply(grid,source)._1,
                        smartConstraints.map(c -> c.apply(grid, source)._2).reduce(Constraint.or));
    }
}
