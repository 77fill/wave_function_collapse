package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSet;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GenerateTileConstraints {
    @Autowired
    private TileSet tiles;
    @Autowired
    private View view;

    @Async("background")
    public void run() {
        tiles.generateConstraints();
    }

    @Async("display")
    private void restraintsGenerated() {
        view.restraintsGenerated();
    }

}
