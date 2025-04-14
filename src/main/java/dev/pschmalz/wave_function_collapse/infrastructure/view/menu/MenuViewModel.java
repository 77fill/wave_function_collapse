package dev.pschmalz.wave_function_collapse.infrastructure.view.menu;

import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import processing.core.PVector;

@AllArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@Getter
@Setter
public class MenuViewModel {
    PVector upperLeft;
    final ChooseTileImages chooseTileImages;
    final GenerateTileConstraints generateTileConstraints;
    final WaveFunctionCollapse waveFunctionCollapse;
    int width, height, background;
    int distanceEdge = 10, buttonSpacing = 10, buttonHeight = 30, buttonWidth = 100;

    public Void handleChooseTileImages() {
        chooseTileImages.run();
        return null;
    }

    public PVector nextButtonUpperLeft(int buttonCount) {
        return new PVector(
                distanceEdge,
                distanceEdge + (buttonHeight+buttonSpacing)*buttonCount);
    }

    public Void handleWaveFunctionCollapse() {
        waveFunctionCollapse.run();
        return null;
    }

    public Void handleShowGrid() {

        return null;
    }

    public Void handleShowTileImages() {

        return null;
    }
}
