package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSet;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

public class ShowTileImages {
    @Autowired
    private TileSet tiles;
    @Autowired
    private View view;

    @Async("display")
    public void run() {
        view.clear();
        view.showImages(tiles.map(Tile::getImage));
    }
}
