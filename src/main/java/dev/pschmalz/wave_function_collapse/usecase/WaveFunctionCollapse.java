package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.ConstraintApplicationCascade;
import dev.pschmalz.wave_function_collapse.domain.MemoryGridStore;
import dev.pschmalz.wave_function_collapse.domain.MemoryTileStore;
import dev.pschmalz.wave_function_collapse.domain.TileSlotGridGenerator;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import reactor.core.Disposable;

import java.util.function.Consumer;

public class WaveFunctionCollapse extends Usecase {
    private final MemoryTileStore tileStore;
    private final TileSlotGridGenerator tileSlotGridGenerator;
    private final ConstraintApplicationCascade constraintApplicationCascade;
    private final MemoryGridStore gridStore;

    @Override
    protected void handleRun() {
        disposable =
        tileStore
                .getTiles()
                .transform(tileSlotGridGenerator.allPossibilities(30,30))
                .map(constraintApplicationCascade)
                .subscribe(
                        gridStore::set,
                        this::onException,
                        this::onSuccess
                );
    }

    @Override
    protected void handleCancel() {
        disposable.dispose();
    }

    private Disposable disposable;

    public WaveFunctionCollapse(Consumer<Event> eventEmitter, MemoryTileStore tileStore, TileSlotGridGenerator tileSlotGridGenerator, ConstraintApplicationCascade constraintApplicationCascade, MemoryGridStore gridStore) {
        super(eventEmitter);
        this.tileStore = tileStore;
        this.tileSlotGridGenerator = tileSlotGridGenerator;
        this.constraintApplicationCascade = constraintApplicationCascade;
        this.gridStore = gridStore;
    }
}
