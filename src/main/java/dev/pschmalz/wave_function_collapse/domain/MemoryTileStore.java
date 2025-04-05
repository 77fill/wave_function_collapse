package dev.pschmalz.wave_function_collapse.domain;

import com.google.common.collect.ImmutableList;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class MemoryTileStore {
    private final Set<Tile> tiles = new CopyOnWriteArraySet<>();
    private final Lock tilesLock = new ReentrantLock();

    public void createTileFromImage(Image image) {
        tilesLock.lock();
        tiles.add(new Tile(image));
        tilesLock.unlock();
    }

    public Flux<Image> getImages() {
        return getTiles().map(Tile::getImage);
    }

    public Flux<Tile> getTiles() {
        return Flux.fromIterable(tiles)

                .doOnSubscribe(s -> tilesLock.lock())
                .doOnTerminate(tilesLock::unlock);
    }

    private void addTile(Tile tile) {
        tilesLock.lock();
        tiles.add(tile);
        tilesLock.unlock();
    }

    private List<Tile> asList() {
        tilesLock.lock();
        var tileList = tiles.stream().toList();
        tilesLock.unlock();

        return tileList;
    }

    private void delete(List<Tile> toBeDeleted) {
        tilesLock.lock();
        toBeDeleted.forEach(tiles::remove);
        tilesLock.unlock();
    }

    public BaseSubscriber<Tile> addTiles(Consumer<Throwable> onException, Runnable onSuccess) {
        return new SubscriberToTiles(onException, onSuccess, this);
    }

    private static class SubscriberToTiles extends BaseSubscriber<Tile> {
        private final Consumer<Throwable> onException;
        private final Runnable onSuccess;
        private final MemoryTileStore tileStore;
        private List<Tile> toBeDeleted;

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            toBeDeleted = ImmutableList.copyOf(tileStore.asList());
        }

        @Override
        protected void hookOnNext(Tile tile) {
            tileStore.addTile(tile);
        }

        @Override
        protected void hookOnComplete() {
            onSuccess.run();
        }

        @Override
        protected void hookOnError(Throwable throwable) {
            onException.accept(throwable);
        }

        @Override
        protected void hookOnCancel() {
            super.hookOnCancel();
        }

        @Override
        protected void hookFinally(SignalType type) {
            tileStore.delete(toBeDeleted);
        }

        public SubscriberToTiles(Consumer<Throwable> onException, Runnable onSuccess, MemoryTileStore tileStore) {
            this.onException = onException;
            this.onSuccess = onSuccess;
            this.tileStore = tileStore;
        }
    }
}
