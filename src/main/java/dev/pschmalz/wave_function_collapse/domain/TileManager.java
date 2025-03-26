package dev.pschmalz.wave_function_collapse.domain;

import java.io.File;
import java.util.Set;

public class TileManager {
    private Set<Tile> tiles;

    public void add(File image) {
        tiles.add(new Tile(image));
    }
}
