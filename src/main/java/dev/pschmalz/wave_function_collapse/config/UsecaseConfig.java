package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.domain.*;
import dev.pschmalz.wave_function_collapse.usecase.*;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
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
    private ConstraintApplicationCascade constraintApplicationCascade;
    @Autowired
    private MemoryGridStore gridStore;

    @Bean
    public LoadChosenTileImages loadChosenTileImages() {
        return new LoadChosenTileImages(usecaseEventEmitter, fileSystemStore, fileChooser, tileStore);
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
    public WaveFunctionCollapse waveFunctionCollapse() {
        return new WaveFunctionCollapse(usecaseEventEmitter, tileStore, tileSlotGridGenerator, constraintApplicationCascade, gridStore);
    }

    @Bean
    public ShowTileSlotGrid showTileSlotGrid() {
        return new ShowTileSlotGrid(usecaseEventEmitter, view, gridStore);
    }
}
