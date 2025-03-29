package dev.pschmalz.wave_function_collapse.domain.collections_tuples;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TileSlotGrid {
    private TileSlot[][] tileSlots;

    public Optional<TileSlot> get(int x, int y) {
        if(x < 0 || y < 0 || x >= tileSlots.length || y >= tileSlots[0].length)
            return Optional.empty();

        return Optional.of(tileSlots[x][y]);
    }
}
