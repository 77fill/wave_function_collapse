package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.domain.MemoryTileStore;
import dev.pschmalz.wave_function_collapse.usecase.LoadChosenTileImages;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
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

    @Bean
    public LoadChosenTileImages loadChosenTileImages() {
        return new LoadChosenTileImages(usecaseEventEmitter, fileSystemStore, fileChooser, tileStore);
    }
}
