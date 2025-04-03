package dev.pschmalz.wave_function_collapse.domain_workers.wfc;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.domain.workers.ModelSynthesis;
import org.springframework.stereotype.Component;

@Component
public class WFC implements ModelSynthesis {
    private TileSlotGrid grid;
    private TimeLoop timeLoop = new TimeLoop();

    @Override
    public void accept(TileSlotGrid grid) {
        this.grid = grid;

        if(!grid.isInitialized())
            throw new IllegalStateException("tileSlotGrid is not initialized!");

        while (grid.hasSuperposition()) {
            timeLoop.do_(this::step)
                    .while_(grid::isDeadEnd)
                    .maxRepeat(20)
                    .start();
        }
    }

    private void step() {
        var collapsedSlot = grid.getTileSlot_withLeastPossibilities().get().randomCollapse();

        grid.tileSlots()
                .sorted(OrderOf_CascadeOf_ConstraintApplication.startingFrom(collapsedSlot))
                .forEach(TileSlot::applyConstraintsOnTargets);


    }
}
