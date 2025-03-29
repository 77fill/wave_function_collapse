package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;

import java.util.Optional;
import java.util.Set;

import static dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotDirection.from;

public class TileSlot {
    public int x, y;
    private TileSlotGrid tileSlotGrid;
    private Set<Tile> possibleTiles;

    public Optional<Constraint> getConstraintFor(TileSlot target) {
        return possibleTiles.stream()
                .map(tile -> tile.getConstraintFor(from(this).to(target)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Constraint.and);
    }

    public Optional<TileSlot> rightNeighbor() {
        return tileSlotGrid.get(x+1,y);
    }
    public Optional<TileSlot> leftNeighbor() {
        return tileSlotGrid.get(x-1,y);
    }
    public Optional<TileSlot> topNeighbor() {
        return tileSlotGrid.get(x,y-1);
    }
    public Optional<TileSlot> bottomNeighbor() {
        return tileSlotGrid.get(x,y+1);
    }
}
