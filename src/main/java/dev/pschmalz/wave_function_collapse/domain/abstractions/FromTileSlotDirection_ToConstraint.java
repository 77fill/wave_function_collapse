package dev.pschmalz.wave_function_collapse.domain.abstractions;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotDirection;

import java.util.Optional;
import java.util.function.Function;

import static dev.pschmalz.wave_function_collapse.domain.abstractions.TileSlotRelation.*;
import static dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotDirection.from;

public class FromTileSlotDirection_ToConstraint implements Function<TileSlotDirection, Optional<Constraint>> {
    private TileSlotRelation condition;
    private Constraint constraint;

    public Optional<Constraint> getConstraintFor(TileSlotDirection pair) {
        return condition.isFulfilledBy(pair.origin,pair.target)?
                    Optional.of(constraint)
                    : Optional.empty();
    }

    public FromTileSlot_ToConstraint getConstraintFor(TileSlot origin) {
        return target -> getConstraintFor(from(origin).to(target));
    }

    public static Builder give(Constraint constraint) {
        return new Builder(constraint);
    }

    private FromTileSlotDirection_ToConstraint(TileSlotRelation condition, Constraint constraint) {
        this.condition = condition;
        this.constraint = constraint;
    }

    static FromTileSlotDirection_ToConstraint constrainRightNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(rightNeighbor_ofOrigin_isTarget);
    }

    static FromTileSlotDirection_ToConstraint constrainLeftNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(leftNeighbor_ofOrigin_isTarget);
    }

    static FromTileSlotDirection_ToConstraint constrainTopNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(topNeighbor_ofOrigin_isTarget);
    }

    static FromTileSlotDirection_ToConstraint constrainBottomNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(bottomNeighbor_ofOrigin_isTarget);
    }

    @Override
    public Optional<Constraint> apply(TileSlotDirection tileSlotDirection) {
        return getConstraintFor(tileSlotDirection);
    }

    public static class Builder {
        private TileSlotRelation condition;
        private Constraint constraint;

        private Builder(Constraint constraint) {
            this.constraint = constraint;
        }

        public FromTileSlotDirection_ToConstraint onlyIf(TileSlotRelation condition) {
            this.condition = condition;
            return build();
        }

        private FromTileSlotDirection_ToConstraint build() {
            return new FromTileSlotDirection_ToConstraint(condition, constraint);
        }
    }
}
