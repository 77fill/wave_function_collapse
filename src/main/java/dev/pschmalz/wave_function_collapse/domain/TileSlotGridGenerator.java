package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class TileSlotGridGenerator {
    public Function<Flux<Tile>, Mono<TileSlotGrid>> allPossibilities(int width, int height) {
        return null;
    }
}
