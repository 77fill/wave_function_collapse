package dev.pschmalz.wave_function_collapse.domain.basic_elements;

import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.Value;

import java.util.Random;

import static io.vavr.API.Set;



@Value
public class TileSlotGrid {
    Set<Tile> tiles;
    Random random;
    List<List<TileSlot>> tileSlots;

    public Option<TileSlot> get(int x, int y) {
        if(contains(x,y)) {
            return getColumn(x).map(col -> col.get(y));
        }
        else
            return Option.none();
    }

    public Option<List<TileSlot>> getColumn(int x) {
        if(0 <= x && x < getWidth())
            return Option.of(tileSlots.get(0));
        else
            return Option.none();
    }

    public int getWidth() {
        return tileSlots.length();
    }

    public int getHeight() {
        return getColumn(0).map(List::length).getOrElse(0);
    }


    public boolean hasSuperposition() {
        return tileSlots().exists(TileSlot::isSuperposition);
    }

    public Stream<TileSlot> tileSlots() {
        return tileSlots.toStream().flatMap(Function1.identity());
    }

    public Option<TileSlotGrid> randomCollapse(TileSlot tileSlot) { return randomCollapse(tileSlot.x, tileSlot.y); }
    public Option<TileSlotGrid> randomCollapse(int x, int y) {
        return 
                get(x,y).map(TileSlot::getPossibleTiles)
                        .map(this::chooseRandomly)
                        .flatMap(updatePossibleTilesOf(x, y))
                        .flatMap(this::withTileSlot);
    }
    


    private Option<TileSlotGrid> withTileSlot(TileSlot tileSlot) {
        if(this.contains(tileSlot.x, tileSlot.y))
            return Option.of(
                    new TileSlotGrid(
                            tiles,
                            random,
                            tileSlots.update(tileSlot.x, col -> col.update(tileSlot.y, tileSlot))));
        else 
            return Option.none();
    }

    private boolean contains(int x, int y) {
        return 0 <= x && x < getWidth()
                && 0 <= y && y < getHeight();
    }

    private Function1<Tile, Option<TileSlot>> updatePossibleTilesOf(int x, int y) {
        return tile -> get(x, y).map(slot -> slot.withPossibleTiles(Set(tile)));
    }

    private Tile chooseRandomly(Set<Tile> tiles) {
        return tiles.toStream().shuffle(random).get();
    }

    public TileSlot getWithLeastPossibilities() {
        return tileSlots().min().get();
    }


    public boolean isDeadEnd() {
        return tileSlots().exists(TileSlot::hasNoPossibilities);
    }
}
