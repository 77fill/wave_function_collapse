package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.domain.workers.ConstraintAppender;
import dev.pschmalz.wave_function_collapse.domain.workers.TileSlotGridGenerator;
import dev.pschmalz.wave_function_collapse.domain.workers.WaveFunctionCollapse;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.InitTempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UsecaseConfig {
    @Autowired
    private FileChooser fileChooser;
    @Autowired
    private FileSystemStore fileSystemStore;
    @Autowired
    private ClasspathStore classpathStore;
    @Autowired
    private ConstraintAppender constraintAppender;
    @Autowired
    private TileSlotGridGenerator tileSlotGridGenerator;
    @Autowired
    private WaveFunctionCollapse waveFunctionCollapse;


    @Bean
    public ChooseTileImages chooseTileImages() {
        return new ChooseTileImages(fileSystemStore, fileChooser);
    }

    @Bean
    public InitTempDirectory initTempDirectory() {
        return new InitTempDirectory(classpathStore, fileSystemStore);
    }

    @Bean
    public GenerateTileConstraints generateTileConstraints() {
        return new GenerateTileConstraints(constraintAppender);
    }

    @Bean
    public dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse waveFunctionCollapse() {
        return new dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse(tileSlotGridGenerator, waveFunctionCollapse, chooseTileImages(), generateTileConstraints());
    }

}
