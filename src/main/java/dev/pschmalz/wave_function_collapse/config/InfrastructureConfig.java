package dev.pschmalz.wave_function_collapse.config;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.infrastructure.classpath_store.ClasspathStoreImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.file_chooser.FileChooserImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.filesystem_store.FileSystemStoreImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.util.Property;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.ViewImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ViewModel;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.menu.Menu;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.MenuViewModel;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.images_grid.ImagesGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ImagesGridViewModel;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import processing.core.PApplet;
import processing.core.PVector;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.vavr.API.Tuple;

//TODO use lombok in configs
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
        var view = new ViewImpl(viewModel(), Tuple(1000,1000), imagesGrid(), menu());

        imagesGrid().setPApplet(view);
        menu().setPApplet(view);

        return view;
    }

    @Bean
    public Menu menu() {
        return new Menu(new PVector(0,0), menuViewModel());
    }

    @Bean
    public MenuViewModel menuViewModel() {
        return new MenuViewModel(
                new PVector(0,0),
                200,
                height,
                imagesGridViewModel(),
                viewModel(),
                chooseTileImages,
                generateTileConstraints,
                waveFunctionCollapse);
    }

    @Bean
    public ViewModel viewModel() {
        return new ViewModel(ViewModel.Scene.None, new PVector(200,0));
    }

    @Bean
    public ImagesGrid imagesGrid() {
        return new ImagesGrid(imagesGridViewModel());
    }

    @Bean
    public ImagesGridViewModel imagesGridViewModel() {
        return new ImagesGridViewModel(
                List.empty(),
                distanceBetween,
                distanceEdge,
                size,
                background,
                width,
                height,
                new PVector(upperLeftX, upperLeftY));
    }
}
