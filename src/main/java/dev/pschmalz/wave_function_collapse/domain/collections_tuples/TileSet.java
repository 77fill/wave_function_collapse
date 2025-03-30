package dev.pschmalz.wave_function_collapse.domain.collections_tuples;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class TileSet {
    private Set<Tile> tiles = new HashSet<>();

    public Set<Tile> getSet() {
        return tiles;
    }

    public void add(File image) {
        tiles.add(new Tile(image));
    }

    public <T> Stream<T> map(Function<Tile,T> mapper) {
        return tiles.stream().map(mapper);
    }

    public void generateConstraints() {

    }
}
