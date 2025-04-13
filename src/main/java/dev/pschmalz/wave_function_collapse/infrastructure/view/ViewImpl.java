package dev.pschmalz.wave_function_collapse.infrastructure.view;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid.ImagesGridViewModel;
import dev.pschmalz.wave_function_collapse.usecase.*;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.stereotypes.Usecase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ViewImpl extends PApplet implements View {
    private final ViewModel viewModel;
    private final ImagesGridViewModel imagesGridViewModel;
    private final ImmutablePair<Integer, Integer> initSize;
    private Optional<Scene> currentScene = Optional.empty();
    private final Queue<Runnable> displayQueue = new ConcurrentLinkedQueue<>();

    @Autowired
    private GenerateTileConstraints generateTileConstraints;
    @Autowired
    private ChooseTileImages chooseTileImages;
    @Autowired
    private WaveFunctionCollapse waveFunctionCollapse;


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


}
