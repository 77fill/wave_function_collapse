package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import dev.pschmalz.wave_function_collapse.domain.Image;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import lombok.Value;
import lombok.With;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Value
public class TileSlot implements Comparable<TileSlot> {
    public int x, y;
    @With
    Set<Tile> possibleTiles;


    public Image getImage() {
        return possibleTiles.get().getImage();
    }

    public boolean isSuperposition() {
        return possibleTiles.size() != 1;
    }

    public int getNumPossibleTiles() {
        return possibleTiles.size();
    }

    @Override
    public boolean equals(Object other) {
        return Match(other).of(
                    Case($(instanceOf(TileSlot.class)), otherSlot -> x == otherSlot.x && y == otherSlot.y),
                    Case($(), false));
    }

    @Override
    public int compareTo(TileSlot other) {
        return getNumPossibleTiles() - other.getNumPossibleTiles();
    }

    public boolean hasNoPossibilities() {
        return possibleTiles.isEmpty();
    }

    public Option<Tile> getTile() {
        if(isSuperposition())
            return Option.none();

        return possibleTiles.toStream().getOption(0);

    }


    public TileSlot applyConstraint(Constraint constraint) {
        return withPossibleTiles(possibleTiles.filter(constraint));
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
