package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.TileManager;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GenerateTileConstraints {
    private TileManager tiles;
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

    public GenerateTileConstraints(TileManager tiles) {
        this.tiles = tiles;
    }
}
