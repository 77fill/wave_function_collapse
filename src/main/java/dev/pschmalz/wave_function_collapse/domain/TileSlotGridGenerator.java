package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class TileSlotGridGenerator {
    private final Random random;

    public Function<Flux<Tile>, Mono<TileSlotGrid>> allPossibilities(int width, int height) {
        return new ActualGenerator(width, height, random);
    }

    public TileSlotGridGenerator(Random random) {
        this.random = random;
    }
}

class ActualGenerator implements Function<Flux<Tile>, Mono<TileSlotGrid>> {
    private final int width, height;
    private final Random random;

    ActualGenerator(int width, int height, Random random) {
        this.width = width;
        this.height = height;
        this.random = random;
    }

    @Override
    public Mono<TileSlotGrid> apply(Flux<Tile> tiles) {
        return tiles.collectList()
                .map(this::noninitializedTileSlotGrid)
                .doOnNext(TileSlotGrid::init);
    }

    private TileSlotGrid noninitializedTileSlotGrid(List<Tile> tiles) {
        return new TileSlotGrid(tiles, width, height, random);
    }
}
