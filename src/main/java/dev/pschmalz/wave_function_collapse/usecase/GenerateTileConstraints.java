package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSet;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GenerateTileConstraints {
    private TileSet tiles;
    private View view;

    @Async("background")
    public void execute(View view) {
        this.view = view;
        tiles.generateConstraints();
    }

    @Async("display")
    private void restraintsGenerated() {
        view.restraintsGenerated();
    }

    public GenerateTileConstraints(TileSet tiles) {
        this.tiles = tiles;
    }
}
