package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import com.google.common.collect.Streams;
import dev.pschmalz.wave_function_collapse.infrastructure.view.Scene;
import processing.core.PVector;

import java.util.Optional;
import java.util.stream.Stream;


public class ImagesGrid implements Scene {
    private final ImagesGridViewModel viewModel;
    private final PAppletDecorator p;

    public ImagesGrid(ImagesGridViewModel viewModel, PAppletDecorator p) {
        this.viewModel = viewModel;
        this.p = p;
    }

    @Override
    public void draw() {
        p.background(viewModel.getBackground());

        Streams.zip(positions(), viewModel.getImages(), PositionedImage::new)
                .filter(PositionedImage::hasSpace)
                .forEachOrdered(p::drawPositionedImage);
    }

    private Stream<Optional<PVector>> positions() {
        return Stream.iterate(
                    PositionInsideGrid.firstPosition(viewModel),
                    pos -> pos.isRightmost()?
                                pos.toTheNextRow()
                                : pos.toTheRight())
                .map(pos -> pos.isOverLowerEdge()?
                                null
                                :pos)
                .map(Optional::ofNullable);
    }

}
