package dev.pschmalz.wave_function_collapse.domain.wfc;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WFC {
    @Autowired
    private TileSlotGrid grid;
    @Autowired
    private TimeLoop timeLoop;

    public void collapse() {
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
