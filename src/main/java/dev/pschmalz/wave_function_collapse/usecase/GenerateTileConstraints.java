package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.TileManager;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;

import java.util.concurrent.Executor;

public class GenerateTileConstraints {
    private TileManager tiles;
    private Executor background, display;
    private View view;

    public void execute(View view) {
        this.view = view;
        background.execute(this::executeProper);
    }

    public void executeProper() {
        tiles.generateConstraints();
        display.execute(view::restraintsGenerated);
    }

    public GenerateTileConstraints(TileManager tiles, Executor background, Executor display) {
        this.tiles = tiles;
        this.background = background;
        this.display = display;
    }
}
