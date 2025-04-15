package dev.pschmalz.wave_function_collapse.infrastructure.gui.view.menu;

import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.MouseAwareElement;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.view.RelativeElement;
import dev.pschmalz.wave_function_collapse.infrastructure.gui.viewmodel.MenuViewModel;
import io.vavr.Function0;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    PApplet pApplet;
    MenuViewModel viewModel; //TODO explain points / coordinates

    List<Button> buttons = new ArrayList<>();

    @PostConstruct
    public void init() {
        button("Choose 3x3 Tile Images", viewModel::handleChooseTileImages);
        button("Show Tile Images", viewModel::handleShowTileImages);
        button("Wave Function Collapse", viewModel::handleWaveFunctionCollapse);
        button("Show Grid", viewModel::handleShowGrid);
    }

    private void button(String text, Function0<Void> clickHandler) {
        buttons.add(
                Button.builder()
                        .text(text)
                        .clickHandler(clickHandler)
                        .width(viewModel.getButtonWidth())
                        .height(viewModel.getButtonHeight())
                        .upperLeft(viewModel.nextButtonUpperLeft(buttons.size()))
                        .pApplet(pApplet)
                .build());
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
