package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.domain.wfc.WFC;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class WaveFunctionCollapse {
    @Autowired
    private WFC wfc;
    @Autowired
    private TileSlotGrid grid;
    @Autowired
    private View view;

    @Async("background")
    public void run() {
        grid.initialize();
        wfc.collapse();
        finishedWFC();
    }

    @Async("display")
    private void finishedWFC() {
        view.finishedWFC();
    }
}
