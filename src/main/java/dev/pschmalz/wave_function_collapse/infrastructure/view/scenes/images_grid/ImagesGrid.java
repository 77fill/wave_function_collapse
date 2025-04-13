package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import com.google.common.collect.Streams;
import dev.pschmalz.wave_function_collapse.infrastructure.view.Scene;
import io.vavr.Function3;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import processing.core.PApplet;
import processing.core.PVector;

import static io.vavr.API.Function;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ImagesGrid implements Scene {
    ImagesGridViewModel viewModel;
    PApplet pApplet;

    @Delegate
    @NonFinal
    PAppletDecorator pAppletDecorator;

    @PostConstruct
    public void init() {
        pAppletDecorator = new PAppletDecorator(pApplet, viewModel);
    }

    @Override
    public void draw() {
        background(viewModel.getBackground());

        positions().zipWith(viewModel.getImages(), PositionedImage::new)
                .filter(PositionedImage::hasSpace)
                .map(drawPositionedImage.apply(viewModel.getSize(), viewModel.getSize()));
    }

    private Stream<Option<PVector>> positions() {
        return Stream.iterate(
                    PositionInsideGrid.firstPosition(viewModel),
                    pos -> pos.isRightmost()?
                                pos.toTheNextRow()
                                : pos.toTheRight())
                .map(pos -> pos.isOverLowerEdge()?
                                Option.none()
                                :Option.of(pos));
    }

    private final Function3<Integer, Integer, PositionedImage, Void> drawPositionedImage = Function(this::drawPositionedImage);

}
