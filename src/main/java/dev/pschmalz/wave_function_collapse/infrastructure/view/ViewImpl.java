package dev.pschmalz.wave_function_collapse.infrastructure.view;

import dev.pschmalz.wave_function_collapse.infrastructure.view.menu.Menu;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import io.vavr.Tuple2;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import processing.core.PApplet;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ViewImpl extends PApplet implements View {
    ViewModel viewModel;
    Tuple2<Integer,Integer> initSize;
    Menu menu;

    Queue<Runnable> displayQueue = new ConcurrentLinkedQueue<>();

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
        viewModel.getCurrentScene().peek(Scene::draw);

        displayQueue.forEach(Runnable::run);
    }

    @Override
    public void mouseClicked() {

    }
}
