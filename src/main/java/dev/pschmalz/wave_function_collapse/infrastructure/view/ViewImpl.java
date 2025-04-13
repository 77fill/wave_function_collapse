package dev.pschmalz.wave_function_collapse.infrastructure.view;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid.ImagesGridViewModel;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import io.vavr.Tuple2;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ViewImpl extends PApplet implements View {
    ViewModel viewModel;
    ImagesGridViewModel imagesGridViewModel;
    Tuple2<Integer,Integer> initSize;
    GenerateTileConstraints generateTileConstraints;
    ChooseTileImages chooseTileImages;
    WaveFunctionCollapse waveFunctionCollapse;

    @NonFinal
    Optional<Scene> currentScene = Optional.empty();
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
        viewModel.getCurrentScene().ifPresent(Scene::draw);

        displayQueue.forEach(Runnable::run);
    }


}
