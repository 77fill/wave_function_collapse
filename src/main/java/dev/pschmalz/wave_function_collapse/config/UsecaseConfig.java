package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.domain.workers.ConstraintAppender;
import dev.pschmalz.wave_function_collapse.domain.workers.TileSlotGridGenerator;
import dev.pschmalz.wave_function_collapse.domain.workers.WaveFunctionCollapse;
import dev.pschmalz.wave_function_collapse.usecase.*;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.stereotypes.Usecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class UsecaseConfig {
    @Autowired
    private Consumer<Usecase.Event> usecaseEventEmitter;
    @Autowired
    private FileChooser fileChooser;
    @Autowired
    private FileSystemStore fileSystemStore;
    @Autowired
    private MemoryTileStore tileStore;
    @Autowired
    private ClasspathStore classpathStore;
    @Autowired
    private View view;
    @Autowired
    private ConstraintAppender constraintAppender;
    @Autowired
    private TileSlotGridGenerator tileSlotGridGenerator;
    @Autowired
    private WaveFunctionCollapse waveFunctionCollapse;
    @Autowired
    private MemoryGridStore gridStore;

    @Bean
    public ChooseTileImages loadChosenTileImages() {
        return new ChooseTileImages(usecaseEventEmitter, fileSystemStore, fileChooser, tileStore);
    }

    @Bean
    public InitTempDirectory initTempDirectory() {
        return new InitTempDirectory(usecaseEventEmitter, classpathStore, fileSystemStore);
    }

    @Bean
    public ShowTileImages showTileImages() {
        return new ShowTileImages(usecaseEventEmitter, tileStore, view);
    }

    @Bean
    public GenerateTileConstraints generateTileConstraints() {
        return new GenerateTileConstraints(usecaseEventEmitter, tileStore, constraintAppender);
    }

    @Bean
    public dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse waveFunctionCollapse() {
        return new dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse(usecaseEventEmitter, tileStore, tileSlotGridGenerator, waveFunctionCollapse, gridStore);
    }

    @Bean
    public ShowTileSlotGrid showTileSlotGrid() {
        return new ShowTileSlotGrid(usecaseEventEmitter, view, gridStore);
    }
}
