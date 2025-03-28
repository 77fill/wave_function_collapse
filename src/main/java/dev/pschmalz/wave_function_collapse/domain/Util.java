package dev.pschmalz.wave_function_collapse.domain;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

import static java.util.function.IntUnaryOperator.identity;

@Component
public class Util {
    public static DirectedConstraintBuilder give(Constraint constraint) {
        return new DirectedConstraintBuilder(constraint);
    }

    public static UnaryOperator<Field> self() {
        return field -> field;
    }

    public static UnaryOperator<Field> toRight() {
        return toFieldWithCoords(x -> x++, identity());
    }
    public static UnaryOperator<Field> toLeft() {
        return toFieldWithCoords(x -> x--, identity());
    }
    public static UnaryOperator<Field> toTop() {
        return toFieldWithCoords(identity(), y -> y--);
    }
    public static UnaryOperator<Field> toBottom() {
        return toFieldWithCoords(identity(), y -> y++);
    }

    public static UnaryOperator<Field> toFieldWithCoords(IntUnaryOperator mapX, IntUnaryOperator mapY) {
        return field -> field
                .getAllFields()
                .get(
                        mapX.applyAsInt(field.x),
                        mapY.applyAsInt(field.y))
                .orElse(null);
    }

    public static class DirectedConstraintBuilder {
        private Constraint constraint;
        private UnaryOperator<Field> moveConstrainor, moveConstrainee;

        public DirectedConstraintBuilder(Constraint constraint) {
            this.constraint = constraint;
        }

        public DirectedConstraintBuilder onlyIf(UnaryOperator<Field> moveConstrainor, UnaryOperator<Field> moveConstrainee) {
            this.moveConstrainee = moveConstrainee;
            this.moveConstrainor = moveConstrainor;

            return this;
        }

        public DirectedConstraint referenceTheSameField() {
            return (constrainor, constrainee) -> {
                if(moveConstrainor.apply(constrainor) == moveConstrainee.apply(constrainee))
                    return Optional.of(constraint);
                else
                    return Optional.empty();
            };
        }
    }
}
