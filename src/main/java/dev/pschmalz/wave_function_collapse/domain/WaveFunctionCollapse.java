package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Constraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.domain_workers.wfc.OrderOf_CascadeOf_ConstraintApplication;
import dev.pschmalz.wave_function_collapse.domain_workers.wfc.TimeLoop;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class WaveFunctionCollapse implements UnaryOperator<TileSlotGrid> {

    @Override
    public TileSlotGrid apply(TileSlotGrid grid) {
        return new WaveFunctionCollapseSpecific(grid).get();
    }
}

class WaveFunctionCollapseSpecific implements Supplier<TileSlotGrid> {
    private final TimeLoop<TileSlotGrid> timeLoop = new TimeLoop<>();
    private final TileSlotGrid grid;

    @Override
    public TileSlotGrid get() {
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
        var collapsedSlot = grid.getTileSlot_withLeastPossibilities().get().randomCollapse();

        return constraintApplicationCascade(grid, List.of(collapsedSlot));
    }

    private TileSlotGrid constraintApplicationCascade(TileSlotGrid grid, List<TileSlot> applyConstraintsInsideThese) {
        return Flux.fromIterable(applyConstraintsInsideThese)
                .flatMap(slot -> slot.getSmartConstraints().map(c -> Tuples.of(slot, c)))
                .map(pair -> pair.getT2().apply(pair.getT1()))
                .reduce(grid, this::applyConstraint)
                .block();

    }

    private TileSlotGrid applyConstraint(TileSlotGrid grid, Tuple2<Optional<TileSlot>, Constraint> pair) {
        var target = pair.getT1();
        var constraint = pair.getT2();

        if(target.isEmpty()) return grid;
        var newGrid = grid.copy();
        newGrid.get(target.get().x, target.get().y).get().apply(Optional.of(constraint));
        return newGrid;
    }

    WaveFunctionCollapseSpecific(TileSlotGrid grid) {
        this.grid = grid;
    }
}