package dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.TileSlot;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.util.ImageUtil;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.util.Property;
import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages;
import dev.pschmalz.wave_function_collapse.usecase.GenerateTileConstraints;
import dev.pschmalz.wave_function_collapse.usecase.WaveFunctionCollapse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import processing.core.PVector;

@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
@Getter
@Setter
public class MenuViewModel {
    final ImagesGridViewModel imagesGridViewModel, tileSlotGridViewModel;
    final ViewModel mainViewModel;
    PVector upperLeft;
    final ChooseTileImages chooseTileImages;
    final GenerateTileConstraints generateTileConstraints;
    final WaveFunctionCollapse waveFunctionCollapse;
    int width, height, background = 255;
    int distanceEdge = 10, buttonSpacing = 10, buttonHeight = 30, buttonWidth = 150;
    Property<Boolean>
            chooseTileImagesActive = new Property<>(true),
            waveFunctionCollapseActive = new Property<>(false),
            showGridActive = new Property<>(false),
            showTileImagesActive = new Property<>(false);

    public MenuViewModel(ImagesGridViewModel tileSlotGridViewModel, PVector upperLeft, int width, int height, ImagesGridViewModel imagesGridViewModel, ViewModel mainViewModel, ChooseTileImages chooseTileImages, GenerateTileConstraints generateTileConstraints, WaveFunctionCollapse waveFunctionCollapse) {
        this.tileSlotGridViewModel = tileSlotGridViewModel;
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.imagesGridViewModel = imagesGridViewModel;
        this.mainViewModel = mainViewModel;
        this.chooseTileImages = chooseTileImages;
        this.generateTileConstraints = generateTileConstraints;
        this.waveFunctionCollapse = waveFunctionCollapse;
    }

    public Void handleChooseTileImages() {
        chooseTileImages.run();
        chooseTileImages.andThen(attempt -> attempt.andThen(
                () -> {
                    showTileImagesActive.setValue(true);
                    showGridActive.setValue(true);
                }));
        return null;
    }

    public PVector nextButtonUpperLeft(int buttonCount) {
        return new PVector(
                distanceEdge,
                distanceEdge + (buttonHeight+buttonSpacing)*buttonCount);
    }

    public Void handleWaveFunctionCollapse() {
        waveFunctionCollapse.run();
        waveFunctionCollapse.andThen(attempt -> attempt.andThen(() -> showGridActive.setValue(true)));
        return null;
    }

    public Void handleShowGrid() {
        var slotGrid = waveFunctionCollapse.get();
        tileSlotGridViewModel.setImages(slotGrid.tileSlots().map(TileSlot::getImage).map(ImageUtil::toPImage).toList());
        tileSlotGridViewModel.setHeight(slotGrid.getHeight()* tileSlotGridViewModel.getSize());
        tileSlotGridViewModel.setWidth(slotGrid.getWidth()* tileSlotGridViewModel.getSize());
        mainViewModel.setCurrentScene(ViewModel.Scene.TileSlotGrid);
        return null;
    }

    public Void handleShowTileImages() {
        imagesGridViewModel.setImages(chooseTileImages.get().toList().map(Tile::getImage).map(ImageUtil::toPImage));
        mainViewModel.setCurrentScene(ViewModel.Scene.ImagesGrid);
        return null;
    }
}
