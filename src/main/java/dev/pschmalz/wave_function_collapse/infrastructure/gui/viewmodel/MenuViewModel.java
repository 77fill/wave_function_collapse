package dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.util.Property;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
    Property<Boolean>
            chooseTileImagesActive = new Property<>(true),
            waveFunctionCollapseActive = new Property<>(false),
            showGridActive = new Property<>(false),
            showTileImagesActive = new Property<>(false);

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
