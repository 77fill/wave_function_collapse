package dev.pschmalz.wave_function_collapse.infrastructure.gui.view.images_grid;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.Scene;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ImagesGridViewModel;
import io.vavr.Function3;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import processing.core.PApplet;
import processing.core.PVector;

import static io.vavr.API.Function;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ImagesGrid implements Scene {
    ImagesGridViewModel viewModel;
    @NonFinal
    @Setter
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
