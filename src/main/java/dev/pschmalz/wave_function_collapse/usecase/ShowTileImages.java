package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.Tile;
import dev.pschmalz.wave_function_collapse.domain.TileManager;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.scheduling.annotation.Async;

public class ShowTileImages {
    private TileManager tiles;

    @Async("display")
    public void execute(View view) {
        view.clear();
        view.showImages(tiles.map(Tile::getFile));
    }

    public ShowTileImages(TileManager tiles) {
        this.tiles = tiles;
    }
}
