package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.domain.ConstraintAppender;
import dev.pschmalz.wave_function_collapse.domain.MemoryTileStore;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.InitTempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.LoadChosenTileImages;
import dev.pschmalz.wave_function_collapse.usecase.ShowTileImages;
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
}
