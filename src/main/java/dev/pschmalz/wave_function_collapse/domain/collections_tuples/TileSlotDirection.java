package dev.pschmalz.wave_function_collapse.domain.collections_tuples;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;

public class TileSlotDirection {
    public TileSlot origin, target;

    private TileSlotDirection(TileSlot origin, TileSlot target) {
        this.origin = origin;
        this.target = target;
    }

    public static Builder from(TileSlot origin) {
        return new Builder(origin);
    }

    public static class Builder {
        private TileSlot origin;

        private Builder(TileSlot origin) {
            this.origin = origin;
        }

        public TileSlotDirection to(TileSlot target) {
            return new TileSlotDirection(origin, target);
        }
    }
}
