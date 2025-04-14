package dev.pschmalz.wave_function_collapse.config;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.infrastructure.classpath_store.ClasspathStoreImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.file_chooser.FileChooserImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.filesystem_store.FileSystemStoreImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.view.ViewImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.view.ViewModel;
import dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid.ImagesGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid.ImagesGridViewModel;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import processing.core.PVector;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.vavr.API.Tuple;

@Configuration
public class InfrastructureConfig {
    @Value("${view.images-grid.distance-between}")
    private int distanceBetween;
    @Value("${view.images-grid.distance-edge}")
    private int distanceEdge;
    @Value("${view.images-grid.size}")
    private int size;
    @Value("${view.images-grid.background}")
    private int background;
    @Value("${view.images-grid.width}")
    private int width;
    @Value("${view.images-grid.height}")
    private int height;
    @Value("${view.images-grid.upper-left.x}")
    private int upperLeftX;
    @Value("${view.images-grid.upper-left.y}")
    private int upperLeftY;

    @Value("${bundled-resources.allowed-name-suffixes}")
    private List<String> allowedNameSuffixes;
    @Value("${bundled-resources.base-path}")
    private Path resourcesBasePath;
    @Value("${file-system.temp-dir-prefix}")
    private String tempDirPrefix;
    @Autowired
    private ClassPath classPath;
    @Autowired
    private JFileChooser jFileChooser;
    @Autowired
    private GenerateTileConstraints generateTileConstraints;
    @Autowired
    private ChooseTileImages chooseTileImages;
    @Autowired
    private WaveFunctionCollapse waveFunctionCollapse;

    @Bean
    public ClasspathStore classpathStore() {
        return new ClasspathStoreImpl(classPath, allowedNameSuffixes, resourcesBasePath);
    }

    @Bean
    public FileChooser fileChooser() {
        return new FileChooserImpl(jFileChooser);
    }

    @Bean
    public FileSystemStore fileSystemStore() throws IOException {
        return new FileSystemStoreImpl(
                FileSystems.getDefault(),
                Files.createTempDirectory(tempDirPrefix));
    }

    @Bean
    public ViewImpl view() {
        return new ViewImpl(viewModel(), imagesGridViewModel(), Tuple(0,0),generateTileConstraints, chooseTileImages, waveFunctionCollapse);
    }

    @Bean
    public ViewModel viewModel() {
        return new ViewModel();
    }

    @Bean
    public ImagesGrid imagesGrid() {
        return new ImagesGrid(imagesGridViewModel(), view());
    }

    @Bean
    public ImagesGridViewModel imagesGridViewModel() {
        return new ImagesGridViewModel(List.empty(), distanceBetween, distanceEdge, size, background, width, height, new PVector(upperLeftX, upperLeftY));
    }
}
