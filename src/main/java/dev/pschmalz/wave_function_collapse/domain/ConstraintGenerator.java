package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;

import java.util.Set;
import java.util.function.BiFunction;

public interface ConstraintGenerator extends BiFunction<Tile,Tile, Set<SmartConstraint>> {

}
