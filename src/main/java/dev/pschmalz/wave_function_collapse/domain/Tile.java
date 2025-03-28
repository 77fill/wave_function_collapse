package dev.pschmalz.wave_function_collapse.domain;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class Tile {
    private File file;
    private Set<DirectedConstraint> directedConstraints;

    public LocatedTile beeingAt(Field field) {
        return new LocatedTile(field, this);
    }

    public Tile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    private Optional<Constraint> unionConstraintFor(Field constrainor, Field constrainee) {
        return directedConstraints.stream()
                .map(constraintFor(constrainor, constrainee))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Constraint.and);
    }

    private Function<DirectedConstraint, Optional<Constraint>> constraintFor(Field constrainor, Field constrainee) {
        return directedConstraint -> directedConstraint.apply(constrainor, constrainee);
    }

    public record LocatedTile(Field location, Tile tile) {
        public Optional<Constraint> getConstraintFor(Field constrainee) {
            return tile.unionConstraintFor(location,constrainee);
        }
    }
}