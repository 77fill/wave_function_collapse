package dev.pschmalz.wave_function_collapse.domain.workers;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlotGrid;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.collection.HashSet;
import io.vavr.collection.Stream;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Random;

import static io.vavr.API.Function;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TileSlotGridGenerator implements Function3<Integer, Integer, Iterable<Tile>, TileSlotGrid> {
    Random random;

    @Override
    public TileSlotGrid apply(Integer width, Integer height, Iterable<Tile> tiles) {
        var tileSet = HashSet.ofAll(tiles);
        return new TileSlotGrid(
                        tileSet,
                        random,
                        Stream.range(0, width)
                                .map(x -> Stream.range(0, height)
                                        .map(y -> new TileSlot(x, y, tileSet))
                                        .toList())
                                .toList());
    }

    public final Function2<Integer, Integer, Function1<Iterable<Tile>,TileSlotGrid>> withSize =
            Function((w,h)->apply(w,h));
}