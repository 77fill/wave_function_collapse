package dev.pschmalz.wave_function_collapse.infrastructure;

import dev.pschmalz.wave_function_collapse.infrastructure.view.DisplayExecutor;
import dev.pschmalz.wave_function_collapse.infrastructure.view.ImagesView;
import dev.pschmalz.wave_function_collapse.infrastructure.view.SubView;
import dev.pschmalz.wave_function_collapse.usecase.*;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processing.core.PApplet;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class MainPApplet extends PApplet implements View {
    @Autowired
    private LoadResources_IntoTempDirectory loadResources_intoTempDirectory;
    @Autowired
    private ChooseTileImages_CreateTiles chooseTileImages_createTiles;
    @Autowired
    private GenerateTileConstraints generateTileConstraints;
    @Autowired
    private ShowTileImages showTileImages;
    private Optional<SubView> toBeShown = Optional.empty();
    @Autowired
    private ImagesView imagesView;
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
    public void tempDirectoryLoaded() {
        chooseTileImages_createTiles.run();
    }

    @Override
    public void tilesLoaded() {
        showTileImages.run();
        //generateTileRestraints.execute(this);
    }

    @Override
    public void showImages(Stream<File> images) {
        imagesView.clear();
        imagesView.set(
                images.map(this.images::get)
                        .peek(i -> {if(i.isEmpty()) throw new IllegalStateException();})
                        .map(Optional::get)
        );
        toBeShown = Optional.of(imagesView);
    }

    @Override
    public void restraintsGenerated() {
        waveFunctionCollapse.run();
    }

    @Override
    public void finishedWFC() {
        showTileSlotGrid.run();
    }

    @Override
    public void clear() {
        toBeShown = Optional.empty();
    }
}
