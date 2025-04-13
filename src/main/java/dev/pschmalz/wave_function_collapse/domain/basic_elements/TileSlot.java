package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import io.vavr.Tuple2;
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

    public Set<SmartConstraint> getSmartConstraints(TileSlotGrid grid) {
        return possibleTiles
                .flatMap(Tile::getConstraints)
                .slideBy(smartConstraint -> smartConstraint.apply(grid, this)._1)
                .map(SmartConstraint::or)
                .toSet();
    }


    public TileSlot applyConstraint(Constraint constraint) {
        return withPossibleTiles(possibleTiles.filter(constraint));
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        private Tuple2<Integer,Integer> xy(TileSlot source) {
            var currentXY = Tuple(source.x, source.y);

            return Match(this).of(
                    Case($(UP), currentXY.map2(y -> y-1)),
                    Case($(DOWN), currentXY.map2(y -> y+1)),
                    Case($(LEFT), currentXY.map1(x -> x-1)),
                    Case($(RIGHT), currentXY.map1(x -> x+1))
            );
        }

        public Option<TileSlot> getNeighbor(TileSlotGrid grid, TileSlot source) {
            var xy = xy(source);
            return grid.get(xy._1, xy._2);
        }
    }
}
