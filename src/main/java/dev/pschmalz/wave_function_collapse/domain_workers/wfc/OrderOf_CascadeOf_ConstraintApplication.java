package dev.pschmalz.wave_function_collapse.domain_workers.wfc;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class OrderOf_CascadeOf_ConstraintApplication implements Comparator<TileSlot> {
    private TileSlot firstSlot;
    private List<TileSlot> orderedTileSlots;
    private int indexOf(TileSlot tileSlot) {return orderedTileSlots.indexOf(tileSlot);}
    private Function<TileSlot, Stream<TileSlot>> getConstraintTargets;

    public static OrderOf_CascadeOf_ConstraintApplication startingFrom(TileSlot firstSlot) {
        return new OrderOf_CascadeOf_ConstraintApplication(firstSlot);
    }

    private OrderOf_CascadeOf_ConstraintApplication(TileSlot firstSlot) {
        this.firstSlot = firstSlot;
        this.getConstraintTargets = TileSlot::getNeighbors;
        this.orderedTileSlots = new ArrayList<>(firstSlot.getGrid().size());
        orderTileSlots();
    }

    private void orderTileSlots() {
        orderedTileSlots =
                appendTransitiveConstraintTargets(
                        Stream.of(),
                        List.of(firstSlot)
                ).toList();
    }

    private Stream<TileSlot> appendTransitiveConstraintTargets(Stream<TileSlot> toAppendTo, List<TileSlot> constraintOrigins) {
        if(constraintOrigins.isEmpty())
            return toAppendTo;

        return Stream.concat(
                toAppendTo,
                appendTransitiveConstraintTargets(
                        constraintOrigins.stream(),
                        constraintOrigins.stream().flatMap(getConstraintTargets).toList()));
    }

    @Override
    public int compare(TileSlot a, TileSlot b) {
        return indexOf(a) - indexOf(b);
    }
}
