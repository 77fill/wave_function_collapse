package dev.pschmalz.wave_function_collapse.infrastructure.view;

import dev.pschmalz.wave_function_collapse.domain.Image;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import dev.pschmalz.wave_function_collapse.usecase.sterotypes.Usecase;
import org.apache.commons.lang3.tuple.ImmutablePair;
import processing.core.PApplet;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ViewImpl extends PApplet implements View, Usecase.EventListener {
    private final ViewModel viewModel;
    private final ImmutablePair<Integer, Integer> initSize;
    private Optional<Scene> currentScene = Optional.empty();
    private final Queue<Runnable> displayQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void settings() {
        size(initSize.getLeft(), initSize.getRight());
        noSmooth();
    }

    public ViewImpl(ViewModel viewModel, ImmutablePair<Integer, Integer> initSize) {
        this.viewModel = viewModel;
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

    }

    @Override
    public void showGrid(TileSlotGrid grid) {

    }

    @Override
    public void handleUsecaseEvent(Usecase.Event usecaseEvent) {

    }
}
