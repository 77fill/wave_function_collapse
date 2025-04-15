package dev.pschmalz.wave_function_collapse.infrastructure.gui.view.menu;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.util.Property;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.MouseAwareElement;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.RelativeElement;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.MenuViewModel;
import io.vavr.Function0;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Menu extends RelativeElement implements MouseAwareElement {
    @Getter
    PVector upperLeft;
    @Getter
    @NonFinal
    @Setter
    PApplet pApplet;
    MenuViewModel viewModel; //TODO explain points / coordinates

    List<Button> buttons = new ArrayList<>();

    @PostConstruct
    public void init() {
        button("Choose 3x3 Tile Images", viewModel::handleChooseTileImages, viewModel.getChooseTileImagesActive());
        button("Show Tile Images", viewModel::handleShowTileImages, viewModel.getShowTileImagesActive());
        button("Wave Function Collapse", viewModel::handleWaveFunctionCollapse, viewModel.getWaveFunctionCollapseActive());
        button("Show Grid", viewModel::handleShowGrid, viewModel.getShowGridActive());
    }

    private void button(String text, Function0<Void> clickHandler, Property<Boolean> active) {
        var button =
                Button.builder()
                        .text(text)
                        .clickHandler(clickHandler)
                        .width(viewModel.getButtonWidth())
                        .height(viewModel.getButtonHeight())
                        .upperLeft(viewModel.nextButtonUpperLeft(buttons.size()))
                        .pApplet(pApplet)
                .build();

        button.activeProperty().bind(active);

        buttons.add(button);
    }

    private void background(int color) {
        fill(color);
        rect(0,0, viewModel.getWidth(), viewModel.getHeight());
    }

    @Override
    protected void relativeDraw() {
        background(viewModel.getBackground());
        buttons.forEach(Button::draw);
    }

    @Override
    public void mouseClicked(PVector relativeMousePosition) {
        if(!isInside(relativeMousePosition)) return;

        buttons.forEach(button -> button.mouseClicked(PVector.sub(relativeMousePosition, button.getUpperLeft())));
    }

    private boolean isInside(PVector position) {
        return 0 <= position.x && position.x < viewModel.getWidth()
                && 0 <= position.y && position.y < viewModel.getHeight();
    }
}
