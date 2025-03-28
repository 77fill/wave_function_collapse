package dev.pschmalz.wave_function_collapse.infrastructure;

import dev.pschmalz.wave_function_collapse.infrastructure.view.ImagesView;
import dev.pschmalz.wave_function_collapse.infrastructure.view.SubView;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages_CreateTiles;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.LoadResources_IntoTempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.ShowTileImages;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import org.springframework.stereotype.Component;
import processing.core.PApplet;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class MainPApplet extends PApplet implements View {
    private LoadResources_IntoTempDirectory loadResources_intoTempDirectory;
    private ChooseTileImages_CreateTiles chooseTileImages_createTiles;
    private GenerateTileConstraints generateTileConstraints;
    private ShowTileImages showTileImages;
    private Optional<SubView> toBeShown;
    private ImagesView imagesView;

    public MainPApplet(LoadResources_IntoTempDirectory loadResources_intoTempDirectory, ChooseTileImages_CreateTiles chooseTileImages_createTiles, ShowTileImages showTileImages, GenerateTileConstraints generateTileConstraints, ImagesView imagesView) {
        this.loadResources_intoTempDirectory = loadResources_intoTempDirectory;
        this.chooseTileImages_createTiles = chooseTileImages_createTiles;
        this.showTileImages = showTileImages;
        this.generateTileConstraints = generateTileConstraints;
        this.imagesView = imagesView;
    }

    @Override
    public void settings() {
        size(1000,1000);
        noSmooth();
    }

    @Override
    public void setup() {
        loadResources_intoTempDirectory.execute(this);
    }

    @Override
    public void draw() {
        toBeShown.ifPresent(SubView::show);
    }

    @Override
    public void tempDirectoryLoaded() {
        chooseTileImages_createTiles.execute(this);
    }

    @Override
    public void tilesLoaded() {
        showTileImages.execute(this);
        //generateTileRestraints.execute(this);
    }

    @Override
    public void showImages(Stream<File> images) {
        imagesView.clear();
        imagesView.set(
                images
                        .map(File::toString)
                        .map(this::loadImage)
        );
        toBeShown = Optional.of(imagesView);
    }

    @Override
    public void restraintsGenerated() {

    }

    @Override
    public void clear() {
        toBeShown = Optional.empty();
    }
}
