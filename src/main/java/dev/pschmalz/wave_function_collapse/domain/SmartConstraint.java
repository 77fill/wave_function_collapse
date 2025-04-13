package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.control.Option;


public interface SmartConstraint extends Function2<TileSlotGrid, TileSlot, Tuple2<Option<TileSlot>, Constraint>> {

}
