package dev.pschmalz.wave_function_collapse.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Field {
    private final Field thisField = this;
    public int x, y;
    private FieldManager fields;
    private Set<Tile> possibleTiles;

    public FieldManager getAllFields() {
        return fields;
    }

    public Optional<Constraint> getConstraintFor(Field constrainee) {
        return possibleTiles.stream()
                .map(tile -> tile.beeingAt(thisField).getConstraintFor(constrainee))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Constraint.and);
    }
}
