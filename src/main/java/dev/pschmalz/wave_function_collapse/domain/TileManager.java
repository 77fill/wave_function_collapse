package dev.pschmalz.wave_function_collapse.domain;

import java.io.File;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class TileManager {
    private Set<Tile> tiles;

    public void add(File image) {
        tiles.add(new Tile(image));
    }

    public <T> Stream<T> map(Function<Tile,T> mapper) {
        return tiles.stream().map(mapper);
    }
}
