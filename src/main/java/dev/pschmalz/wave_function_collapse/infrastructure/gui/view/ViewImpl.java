package dev.pschmalz.wave_function_collapse.infrastructure.gui.view;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.images_grid.ImagesGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.menu.Menu;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ImagesGridViewModel;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ViewModel;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import io.vavr.Tuple2;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ViewImpl extends PApplet implements View {
    ViewModel viewModel;
    Tuple2<Integer,Integer> initSize;
    ImagesGrid imagesGrid;
    ImagesGridViewModel imagesGridViewModel, tileSlotGridViewModel;
    Menu menu;

    Queue<Runnable> displayQueue = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void init() {
        imagesGrid.setPApplet(this);
        menu.setPApplet(this);
    }

    @Override
    public void settings() {
        size(initSize._1, initSize._2);
        noSmooth();
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        menu.draw();
        switch(viewModel.getCurrentScene()) {
            case None -> {}
            case ImagesGrid -> {imagesGrid.setViewModel(imagesGridViewModel); imagesGrid.draw();}
            case TileSlotGrid -> {imagesGrid.setViewModel(tileSlotGridViewModel); imagesGrid.draw();}
        }

        displayQueue.forEach(Runnable::run);
    }

    @Override
    public void mouseClicked() {
        var mousePosition = new PVector(mouseX, mouseY);

        menu.mouseClicked(PVector.sub(mousePosition, menu.getUpperLeft()));
        switch(viewModel.getCurrentScene()) {
            case None -> {}
            case ImagesGrid -> imagesGrid.mouseClicked(PVector.sub(mousePosition, viewModel.getSceneUpperLeft()));
        }

    }
}
