package dev.pschmalz.wave_function_collapse.domain.wfc;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class History {
    private Optional<TileSlot[][]> tileSlots = Optional.empty();
    @Autowired
    private TileSlotGrid grid;

    public void restoreIfExists() {
        tileSlots.ifPresent(oldTiles -> grid.set(oldTiles));
    }

    public void update() {
        var currentTileSlots = grid.getTileSlotArray();
        var currentTileSlotsCopy = new TileSlot[currentTileSlots.length][];
        for(int i = 0; i < currentTileSlots.length; i++)
            currentTileSlotsCopy[i] =
                    Arrays.copyOf(
                            currentTileSlots[i],
                            currentTileSlots[i].length);
        tileSlots = Optional.of(currentTileSlotsCopy);
    }

    public void clear() {
        tileSlots = Optional.empty();
    }

}
