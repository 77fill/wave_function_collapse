package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import io.vavr.Function2;

public interface ConstraintGenerator extends Function2<Tile,Tile, SmartConstraint> {

}
