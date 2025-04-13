package dev.pschmalz.wave_function_collapse.usecase.interfaces;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlotGrid;

public interface View {
    void showImage(Image image);

    void showGrid(TileSlotGrid grid);
}
