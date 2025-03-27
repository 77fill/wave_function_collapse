package dev.pschmalz.wave_function_collapse.infrastructure;

import com.google.common.collect.ImmutableList;
import dev.pschmalz.wave_function_collapse.infrastructure.view.ImagesView;
import dev.pschmalz.wave_function_collapse.infrastructure.view.SubView;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages_CreateTiles;
import dev.pschmalz.wave_function_collapse.usecase.LoadResources_IntoTempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.ShowTileImages;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

public class MainPApplet extends PApplet implements View {
    private Queue<Runnable> eventQueue;
    private LoadResources_IntoTempDirectory loadResources_intoTempDirectory;
    private ChooseTileImages_CreateTiles chooseTileImages_createTiles;
    private ShowTileImages showTileImages;
    private Optional<SubView> toBeShown;
    private ImagesView imagesView;

    public MainPApplet(Queue<Runnable> eventQueue, LoadResources_IntoTempDirectory loadResources_intoTempDirectory, ChooseTileImages_CreateTiles chooseTileImages_createTiles, ShowTileImages showTileImages) {
        this.eventQueue = eventQueue;
        this.loadResources_intoTempDirectory = loadResources_intoTempDirectory;
        this.chooseTileImages_createTiles = chooseTileImages_createTiles;
        this.showTileImages = showTileImages;
        this.imagesView = new ImagesView(this, new Util());
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

        eventQueue.forEach(Runnable::run);
    }

    @Override
    public void tempDirectoryLoaded() {
        chooseTileImages_createTiles.execute(this);
    }

    @Override
    public void tilesLoaded() {
        showTileImages.execute(this);
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
    public void clear() {
        toBeShown = Optional.empty();
    }
}
