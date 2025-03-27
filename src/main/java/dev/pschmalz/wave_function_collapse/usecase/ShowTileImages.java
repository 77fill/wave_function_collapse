package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.Tile;
import dev.pschmalz.wave_function_collapse.domain.TileManager;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;

import java.util.concurrent.Executor;

public class ShowTileImages {
    private TileManager tiles;
    private Executor display;

    public void execute(View view) {
        display.execute(() -> {
            view.clear();
            view.showImages(tiles.map(Tile::getFile));
        });
    }

    public ShowTileImages(TileManager tiles, Executor display) {
        this.tiles = tiles;
        this.display = display;
    }
}
