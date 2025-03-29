package dev.pschmalz.wave_function_collapse.domain.abstractions;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;

import java.util.Optional;
import java.util.function.BiPredicate;

public interface TileSlotRelation extends BiPredicate<TileSlot, TileSlot> {
    default boolean isFulfilledBy(TileSlot origin, TileSlot target) {
        return test(origin, target);
    }

    TileSlotRelation rightNeighbor_ofOrigin_isTarget =
            (origin, target) -> origin.rightNeighbor().equals(Optional.of(target));

    TileSlotRelation leftNeighbor_ofOrigin_isTarget =
            (origin, target) -> origin.leftNeighbor().equals(Optional.of(target));

    TileSlotRelation topNeighbor_ofOrigin_isTarget =
            (origin, target) -> origin.topNeighbor().equals(Optional.of(target));

    TileSlotRelation bottomNeighbor_ofOrigin_isTarget =
            (origin, target) -> origin.bottomNeighbor().equals(Optional.of(target));
}
