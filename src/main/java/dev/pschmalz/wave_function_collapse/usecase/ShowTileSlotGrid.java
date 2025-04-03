package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.view.ImagesView;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShowTileSlotGrid {
    @Autowired
    private View view;
    @Autowired
    private ImagesView imagesView;
    @Autowired
    private TileSlotGrid tileSlotGrid;

    @Async("display")
    public void run() {
        imagesView.setDistanceBetween(0);
        view.showImages(
                tileSlotGrid.tileSlots()
                            .map(TileSlot::getTile)
                            .peek(tile -> {if(tile.isEmpty()) throw new IllegalStateException();})
                            .map(Optional::get)
                            .map(Tile::getImage)
                            .map(Image::getFile)
        );
    }
}
