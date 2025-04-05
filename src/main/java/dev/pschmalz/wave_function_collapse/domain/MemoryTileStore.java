package dev.pschmalz.wave_function_collapse.domain;

import com.google.common.collect.ImmutableList;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.List;
import java.util.function.Consumer;

public class MemoryTileStore {
    public void createTileFromImage(Image image) {

    }

    public Flux<Image> getImages() {
        throw new IllegalStateException();
    }

    public Flux<Tile> getTiles() {
        return null;
    }

    private void addTile(Tile tile) {

    }

    private List<Tile> asList() {
        return List.of();
    }

    private void delete(List<Tile> toBeDeleted) {

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
