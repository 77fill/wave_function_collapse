package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class ConstraintAppender implements Function<Flux<Tile>,Flux<Tile>> {
    @Override
    public Flux<Tile> apply(Flux<Tile> tileFlux) {
        return null;
    }
}
