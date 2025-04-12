package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import lombok.Value;

import static io.vavr.API.Map;
import static io.vavr.API.Tuple;

@Value
public class ConstraintApplicationHistory {
    Map<TileSlot, Set<Constraint>> applications;

    //TODO EQUALS

    public boolean isUnnecessary(TileSlot tileSlot, Constraint constraint) {
        return applications.get(tileSlot)
                        .map(constraints ->
                                constraints.contains(constraint))
                        .getOrElse(false);
    }

    public ConstraintApplicationHistory update(Stream<Tuple2<Constraint, TileSlot>> applications) {
        return new ConstraintApplicationHistory(
            this.applications.merge(
                    applications.slideBy(Tuple2::_2)
                                .map(apps -> Tuple(
                                        apps.get()._2,
                                        apps.map(Tuple2::_1).toSet()))
                                .toMap(Function1.identity())));
    }

    public static ConstraintApplicationHistory empty() {
        return new ConstraintApplicationHistory(Map());
    }
}
