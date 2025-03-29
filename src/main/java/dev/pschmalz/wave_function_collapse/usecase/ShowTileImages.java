package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSet;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.scheduling.annotation.Async;

public class ShowTileImages {
    private TileSet tiles;

    @Async("display")
    public void execute(View view) {
        view.clear();
        view.showImages(tiles.map(Tile::getImage));
    }

    public ShowTileImages(TileSet tiles) {
        this.tiles = tiles;
    }
}
