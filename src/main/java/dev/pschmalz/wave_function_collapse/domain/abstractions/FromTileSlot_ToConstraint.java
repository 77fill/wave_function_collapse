package dev.pschmalz.wave_function_collapse.domain.abstractions;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;

import java.util.Optional;
import java.util.function.Function;

public interface FromTileSlot_ToConstraint extends Function<TileSlot, Optional<Constraint>> {
    default Optional<Constraint> getConstraintFor(TileSlot target) {
        return apply(target);
    }
}
