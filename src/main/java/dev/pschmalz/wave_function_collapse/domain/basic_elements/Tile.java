package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import dev.pschmalz.wave_function_collapse.domain.abstractions.FromTileSlotDirection_ToConstraint;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotDirection;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotDirection.from;

public class Tile {
    private File image;
    private Set<FromTileSlotDirection_ToConstraint> conditionalConstraints;

    public Tile(File image) {
        this.image = image;
    }

    public File getImage() {
        return image;
    }

    public Optional<Constraint> getConstraintFor(TileSlotDirection pair) {
        return conditionalConstraints.stream()
                .map(getActualConstraintFor(pair.origin, pair.target))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Constraint.and);
    }

    private Function<FromTileSlotDirection_ToConstraint, Optional<Constraint>> getActualConstraintFor(TileSlot origin, TileSlot target) {
        return conditionalConstraint -> conditionalConstraint.getConstraintFor(from(origin).to(target));
    }

}