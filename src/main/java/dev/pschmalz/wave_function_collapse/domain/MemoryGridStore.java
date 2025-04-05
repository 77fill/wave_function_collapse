package dev.pschmalz.wave_function_collapse.domain;

import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import reactor.core.publisher.Mono;

import java.util.MissingResourceException;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MemoryGridStore {
    private Optional<TileSlotGrid> tileSlotGrid;
    private Lock tileSlotGridLock = new ReentrantLock();

    public void set(TileSlotGrid grid) {
        tileSlotGridLock.lock();
        tileSlotGrid = Optional.of(grid);
        tileSlotGridLock.unlock();
    }

    public Mono<TileSlotGrid> get() {
        return Mono.<TileSlotGrid>create(
                sink ->
                   tileSlotGrid.ifPresentOrElse(
                           sink::success,
                           () -> sink.error(
                                   new MissingResourceException(
                                       "MemoryGridStone does not have a tileSlotGrid",
                                       TileSlotGrid.class.getName(),
                                       "tileSlotGrid"
                                   )
                           )
                   )
            )
                .doOnSubscribe(s -> tileSlotGridLock.lock())
                .doOnTerminate(tileSlotGridLock::unlock);
    }
}
