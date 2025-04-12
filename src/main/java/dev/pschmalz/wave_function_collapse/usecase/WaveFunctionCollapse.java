package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.TileSlotGridGenerator;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import io.vavr.concurrent.Future;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import static io.vavr.API.Future;
import static io.vavr.API.Stream;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class WaveFunctionCollapse implements Future<TileSlotGrid> {
    TileSlotGridGenerator tileSlotGridGenerator;
    dev.pschmalz.wave_function_collapse.domain.WaveFunctionCollapse waveFunctionCollapse;
    ChooseTileImages chooseTileImages;
    GenerateTileConstraints generateTileConstraints;

    @Delegate
    @NonFinal
    Future<TileSlotGrid> future;

    public void run() {
        future = Future(this::computation);
    }

    private TileSlotGrid computation() {
        return Stream(chooseTileImages.get())
                    .map(generateTileConstraints)
                    .map(tileSlotGridGenerator.withSize.apply(20,20))
                    .flatMap(waveFunctionCollapse)
                    .get();

    }
}
