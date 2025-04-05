package dev.pschmalz.wave_function_collapse.infrastructure;

import dev.pschmalz.wave_function_collapse.domain.Image;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.view.DisplayExecutor;
import dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid.ImagesGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.view.SubView;
import dev.pschmalz.wave_function_collapse.usecase.*;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processing.core.PApplet;

import java.util.Optional;

@Component
public class MainPApplet extends PApplet implements View, Usecase.EventListener {
    @Autowired
    private LoadResources_IntoTempDirectory loadResources_intoTempDirectory;
    @Autowired
    private LoadChosenTileImages loadChosenTileImages_;
    @Autowired
    private GenerateTileConstraints generateTileConstraints;
    @Autowired
    private ShowTileImages showTileImages;
    private Optional<SubView> toBeShown = Optional.empty();
    @Autowired
    private ImagesGrid imagesGrid;
    @Autowired
    private DisplayExecutor displayExecutor;

    @Autowired
    private Images images;
    @Autowired
    private ShowTileSlotGrid showTileSlotGrid;
    @Autowired
    private WaveFunctionCollapse waveFunctionCollapse;

    @Override
    public void settings() {
        size(1000,1000);
        noSmooth();
    }

    @Override
    public void setup() {
        loadResources_intoTempDirectory.run();
    }

    @Override
    public void draw() {
        toBeShown.ifPresent(SubView::show);

        displayExecutor.runCommands();
    }


    @Override
    public void showImage(Image image) {

    }

    @Override
    public void showGrid(TileSlotGrid grid) {

    }

    @Override
    public void clear() {
        toBeShown = Optional.empty();
    }

    @Override
    public void handleUsecaseEvent(Usecase.Event usecaseEvent) {

    }
}
