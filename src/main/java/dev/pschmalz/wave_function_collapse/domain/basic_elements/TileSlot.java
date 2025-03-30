package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotDirection.from;

public class TileSlot implements Comparable<TileSlot> {
    public int x, y;
    private TileSlotGrid tileSlotGrid;
    private Set<Tile> possibleTiles;

    public Optional<Constraint> getConstraintFor(TileSlot target) {
        return possibleTiles.stream()
                .map(tile -> tile.getConstraintFor(from(this).to(target)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(Constraint.or);
    }

    public boolean isSuperposition() {
        return possibleTiles.size() != 1;
    }

    public int getNumPossibleTiles() {
        return possibleTiles.size();
    }

    public TileSlot randomCollapse() {
        var chosen = (Tile) possibleTiles.toArray()[possibleTiles.size()-1];
        possibleTiles.clear();
        possibleTiles.add(chosen);
        return this;
    }

    public Stream<TileSlot> getNeighbors() {
        return Stream.of(rightNeighbor(),leftNeighbor(),topNeighbor(),bottomNeighbor())
                .filter(Optional::isPresent)
                .map(Optional::get);
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

    public void applyConstraintsOnTargets() {
        getNeighbors().forEach(target -> target.apply(getConstraintFor(target)));
    }

    private void apply(Optional<Constraint> maybeConstraint) {
        if(maybeConstraint.isEmpty())
            return;

        var constraint = maybeConstraint.get();

        possibleTiles.removeIf(Tile.doesNotFulfill(constraint));
    }

    @Override
    public int compareTo(TileSlot other) {
        return getNumPossibleTiles() - other.getNumPossibleTiles();
    }

    public TileSlotGrid getGrid() {
        return tileSlotGrid;
    }

    public boolean hasNoPossibilities() {
        return possibleTiles.isEmpty();
    }

    public Optional<Tile> getTile() {
        if(isSuperposition())
            return Optional.empty();

        return possibleTiles.stream().findFirst();
    }

    public void initialize() {
        possibleTiles = tileSlotGrid.getTileSet();
    }
}
