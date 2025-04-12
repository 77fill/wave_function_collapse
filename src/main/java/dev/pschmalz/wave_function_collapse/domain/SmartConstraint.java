package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import io.vavr.Tuple2;
import io.vavr.control.Option;

import java.util.function.Function;

public interface SmartConstraint extends Function<TileSlot, Tuple2<Option<TileSlot>, Constraint>> {
    default Tuple2<Option<TileSlot>, Constraint> getConstraintStemmingFrom(TileSlot source) {
        return apply(source);
    }

    @Override
    Tuple2<Option<TileSlot>, Constraint> apply(TileSlot source);
}
