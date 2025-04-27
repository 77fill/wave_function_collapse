package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.ViewImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.images_grid.ImagesGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.menu.Menu;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ImagesGridViewModel;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.MenuViewModel;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ViewModel;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import processing.core.PVector;

import static io.vavr.API.Tuple;

//TODO use lombok in configs
@Configuration
public class GUIConfig {
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

    @Autowired
    private GenerateTileConstraints generateTileConstraints;
    @Autowired
    private ChooseTileImages chooseTileImages;
    @Autowired
    private WaveFunctionCollapse waveFunctionCollapse;

    @Bean
    public ViewImpl view() {
        return new ViewImpl(viewModel(), Tuple(1000,1000), imagesGrid(), imagesGridViewModel(), tileSlotGridViewModel(), menu());
    }

    @Bean
    public Menu menu() {
        return new Menu(new PVector(0,0), menuViewModel());
    }

    @Bean
    public MenuViewModel menuViewModel() {
        return new MenuViewModel(
                tileSlotGridViewModel(),
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
    @Primary
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

    @Bean
    public ImagesGridViewModel tileSlotGridViewModel() {
        return new ImagesGridViewModel(
                List.empty(),
                0,
                distanceEdge,
                10,
                background,
                width,
                height,
                new PVector(upperLeftX, upperLeftY));
    }
}
