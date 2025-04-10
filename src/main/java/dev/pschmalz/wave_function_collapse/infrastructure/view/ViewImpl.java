package dev.pschmalz.wave_function_collapse.infrastructure.view;

import dev.pschmalz.wave_function_collapse.domain.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid.ImagesGridViewModel;
import dev.pschmalz.wave_function_collapse.usecase.*;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ViewImpl extends PApplet implements View, Usecase.EventListener {
    private final ViewModel viewModel;
    private final ImagesGridViewModel imagesGridViewModel;
    private final ImmutablePair<Integer, Integer> initSize;
    private Optional<Scene> currentScene = Optional.empty();
    private final Queue<Runnable> displayQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private GenerateTileConstraints generateTileConstraints;
    @Autowired
    private LoadChosenTileImages loadChosenTileImages;
    @Autowired
    private WaveFunctionCollapse waveFunctionCollapse;
    @Autowired
    private ShowTileSlotGrid showTileSlotGrid;

    @Override
    public void settings() {
        size(initSize.getLeft(), initSize.getRight());
        noSmooth();
    }

    public ViewImpl(ViewModel viewModel, ImagesGridViewModel imagesGridViewModel, ImmutablePair<Integer, Integer> initSize) {
        this.viewModel = viewModel;
        this.imagesGridViewModel = imagesGridViewModel;
        this.initSize = initSize;
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        viewModel.getCurrentScene().ifPresent(Scene::draw);

        displayQueue.forEach(Runnable::run);
    }

    @Override
    public void showImage(Image image) {
        var pImage = new PImage(image.getWidth(), image.getHeight(), image.getPixels(), false, this);
        imagesGridViewModel.addImage(pImage);
    }

    @Override
    public void showGrid(TileSlotGrid grid) {
        grid.tileSlots()
                .map(TileSlot::getImage)
                .map(this::fromImage)
                .forEach(imagesGridViewModel::addImage);
    }

    private PImage fromImage(Image image) {
        return new PImage(image.getWidth(), image.getHeight(), image.getPixels(), false, this);
    }

    @Override
    public void handleUsecaseEvent(Usecase.Event usecaseEvent) {
        switch (usecaseEvent.source()) {
            case LoadChosenTileImages ignored:
                if(usecaseEvent.state() == Usecase.State.SUCCESS)
                    generateTileConstraints.run();
                break;
            case InitTempDirectory initTempDirectory:
                if(usecaseEvent.state() == Usecase.State.SUCCESS)
                    loadChosenTileImages.run();
                break;
            case GenerateTileConstraints ignored:
                if(usecaseEvent.state() == Usecase.State.SUCCESS)
                    waveFunctionCollapse.run();
                break;
            case WaveFunctionCollapse ignored:
                if(usecaseEvent.state() == Usecase.State.SUCCESS)
                    showTileSlotGrid.run();
                break;
            default: ;
        }
    }
}
