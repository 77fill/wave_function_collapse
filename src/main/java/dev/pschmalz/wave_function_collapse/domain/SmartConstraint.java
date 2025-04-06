package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import reactor.util.function.Tuple2;

import java.util.Optional;
import java.util.function.Function;

public interface SmartConstraint extends Function<TileSlot, Tuple2<Optional<TileSlot>, Constraint>> {
    default Tuple2<Optional<TileSlot>, Constraint> getConstraintStemmingFrom(TileSlot source) {
        return apply(source);
    }

    @Override
    Tuple2<Optional<TileSlot>, Constraint> apply(TileSlot source);
}
