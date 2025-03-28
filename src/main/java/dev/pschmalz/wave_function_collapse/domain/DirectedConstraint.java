package dev.pschmalz.wave_function_collapse.domain;

import java.util.Optional;
import java.util.function.BiFunction;

import static dev.pschmalz.wave_function_collapse.domain.Util.*;

public interface DirectedConstraint extends BiFunction<Field,Field, Optional<Constraint>> {
    @Override
    Optional<Constraint> apply(Field constrainor, Field constrainee);

    static DirectedConstraint constrainRightNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(toRight(), self()).referenceTheSameField();
    }

    static DirectedConstraint constrainLeftNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(toLeft(), self()).referenceTheSameField();
    }

    static DirectedConstraint constrainTopNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(toTop(), self()).referenceTheSameField();
    }

    static DirectedConstraint constrainBottomNeighbour(Constraint constraint) {
        return give(constraint).onlyIf(toBottom(), self()).referenceTheSameField();
    }

}
