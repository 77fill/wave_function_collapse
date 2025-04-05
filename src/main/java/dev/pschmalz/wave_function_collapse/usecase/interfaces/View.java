package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.domain.Image;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;

import java.io.File;
import java.util.stream.Stream;

public interface View {
    void showImage(Image image);

    void showGrid(TileSlotGrid grid);
}
