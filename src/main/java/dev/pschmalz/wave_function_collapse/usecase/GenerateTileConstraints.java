package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.workers.ConstraintAppender;
import io.vavr.Function1;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static io.vavr.API.Function;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GenerateTileConstraints implements Function1<Iterable<Tile>, Set<Tile>> {
    ConstraintAppender constraintAppender;

    @Override
    public Set<Tile> apply(Iterable<Tile> tile) {
        return computation.apply(tile);
    }

    private Set<Tile> computation(Iterable<Tile> tiles) {
        return constraintAppender.apply(
                    Stream.ofAll(tiles)
            ).toSet();
    }

    Function1<Iterable<Tile>,Set<Tile>> computation = Function(this::computation).memoized();
}
