package dev.pschmalz.wave_function_collapse.domain.collections_tuples;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class TileSlotGrid {
    private TileSlot[][] tileSlots;
    private boolean initialized = false;

    public Optional<TileSlot> get(int x, int y) {
        if(x < 0 || y < 0 || x >= tileSlots.length || y >= tileSlots[0].length)
            return Optional.empty();

        return Optional.of(tileSlots[x][y]);
    }

    public void set(TileSlot[][] tileSlots) {
        this.tileSlots = tileSlots;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public boolean hasSuperposition() {
        return tileSlots().anyMatch(TileSlot::isSuperposition);
    }

    public Stream<TileSlot> tileSlots() {
        return Stream.of(tileSlots).flatMap(Stream::of);
    }

    public Stream<TileSlot> getAllWithSuperposition() {
        return tileSlots().filter(TileSlot::isSuperposition);
    }

    public Optional<TileSlot> getTileSlot_withLeastPossibilities() {
        return tileSlots().min(TileSlot::compareTo);
    }

    public int size() {
        return tileSlots.length * tileSlots[0].length;
    }

    public TileSlot[][] getTileSlotArray() {
        return tileSlots;
    }

    public boolean isDeadEnd() {
        return tileSlots().anyMatch(TileSlot::hasNoPossibilities);
    }
}
