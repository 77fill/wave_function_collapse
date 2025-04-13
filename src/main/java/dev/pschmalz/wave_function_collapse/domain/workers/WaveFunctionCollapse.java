package dev.pschmalz.wave_function_collapse.domain.workers;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.SmartConstraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.domain.util.ConstraintApplicationHistory;
import dev.pschmalz.wave_function_collapse.domain.util.TimeLoop;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static io.vavr.API.*;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WaveFunctionCollapse implements Function1<TileSlotGrid,TileSlotGrid> {
    TimeLoop<TileSlotGrid> timeLoop = new TimeLoop<>();

    @Override
    public TileSlotGrid apply(TileSlotGrid grid) {
        var currentGrid = grid;
        while (currentGrid.hasSuperposition()) {
            currentGrid =
                    timeLoop.apply(this::step)
                            .on(currentGrid)
                            .while_(currentGrid::isDeadEnd)
                            .maxRepeat(20)
                            .start()
                            .getResult();
        }
        return currentGrid;
    }

    private TileSlotGrid step(TileSlotGrid grid) {
        var targetTileSlot = grid.getWithLeastPossibilities();
        var newGrid = grid.randomCollapse(targetTileSlot).get();

        return constraintApplicationCascade(newGrid, Stream(targetTileSlot), ConstraintApplicationHistory.empty());
    }

    private TileSlotGrid constraintApplicationCascade(TileSlotGrid grid, Stream<TileSlot> applyConstraintsInsideThese, ConstraintApplicationHistory applicationHistory) {
        return applyConstraintsInsideThese
                .flatMap(pairs_slot_itsSmartConstraint.apply(grid))
                .flatMap(find_constraint_itsTargetSlot.apply(grid).tupled())
                .flatMap(constraint_updatedTargetSlot.apply(applicationHistory).tupled())
                .transform(pairs ->
                        constraintApplicationCascade(
                                grid,
                                pairs.map(Tuple2::_2),
                                applicationHistory.update(pairs)));

    }

    private final Function3<ConstraintApplicationHistory, Constraint, TileSlot, Option<Tuple2<Constraint, TileSlot>>> constraint_updatedTargetSlot =
            Function(this::constraint_updatedTargetSlot);

    private Option<Tuple2<Constraint, TileSlot>> constraint_updatedTargetSlot(ConstraintApplicationHistory applicationHistory, Constraint constraint, TileSlot tileSlot) {
        if(applicationHistory.isUnnecessary(tileSlot, constraint))
            return Option.none();
        else
            return Option.of(Tuple(constraint, tileSlot.applyConstraint(constraint)));
    }

    private final Function2<TileSlotGrid,TileSlot,Stream<Tuple2<TileSlot, SmartConstraint>>> pairs_slot_itsSmartConstraint = Function(this::pairs_slot_itsSmartConstraint);
    private Stream<Tuple2<TileSlot, SmartConstraint>> pairs_slot_itsSmartConstraint(TileSlotGrid grid, TileSlot tileSlot) {
        return Stream(tileSlot).cycle()
                .zip(tileSlot.getSmartConstraints(grid).toStream());
    }

    private final Function3<TileSlotGrid,TileSlot,SmartConstraint,Option<Tuple2<Constraint,TileSlot>>> find_constraint_itsTargetSlot =
            Function(this::constraint_itsTarget);

    private Option<Tuple2<Constraint, TileSlot>> constraint_itsTarget(TileSlotGrid grid, TileSlot source, SmartConstraint smartConstraint) {
        var target_constraint = smartConstraint.apply(grid,source);
        var target = target_constraint._1;
        var constraint = target_constraint._2;
        return target
                .map(slot -> slot.applyConstraint(constraint))
                .map(slot -> Tuple(constraint, slot));
    }


}
