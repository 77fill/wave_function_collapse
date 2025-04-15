package dev.pschmalz.wave_function_collapse.infrastructure.gui.view.images_grid;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.ImagesGridViewModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import processing.core.PVector;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PositionInsideGrid extends PVector {

    @Delegate
    PVector pVector;
    ImagesGridViewModel viewModel;

    public boolean isOverLowerEdge() {
        return viewModel.getLowerEdge() < pVector.y + viewModel.getSize();
    }

    public PositionInsideGrid toTheNextRow() {
        var newPos = new PVector(
                viewModel.getDistanceEdge(),
                pVector.y
                        + viewModel.getSize()
                        + viewModel.getDistanceBetween());

        return new PositionInsideGrid(newPos, viewModel);
    }

    public PositionInsideGrid toTheRight() {
        var newPos = new PVector(
                pVector.x
                        + viewModel.getSize()
                        + viewModel.getDistanceBetween(),
                pVector.y);
        return new PositionInsideGrid(newPos, viewModel);
    }

    public boolean isRightmost() {
        return viewModel.getRightEdge() < pVector.x + viewModel.getSize();
    }

    static public PositionInsideGrid firstPosition(ImagesGridViewModel viewModel) {
        return new PositionInsideGrid(
                new PVector(
                        viewModel.getDistanceEdge(),
                        viewModel.getDistanceEdge()
                ),
                viewModel
        );
    }
}
