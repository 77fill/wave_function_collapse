package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import io.vavr.Function3;
import static io.vavr.API.Function;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PAppletDecorator {
    PApplet pApplet;
    ImagesGridViewModel viewModel;

    private void afterTranslate(Runnable drawThis) {
        pApplet.pushMatrix();
        pApplet.translate(viewModel.getUpperLeft().x, viewModel.getUpperLeft().y);

        drawThis.run();

        pApplet.popMatrix();
    }

    public void background(int background) {
        afterTranslate(() -> {
            pApplet.rectMode(PConstants.CORNER);
            pApplet.fill(background);
            pApplet.rect(0, 0, viewModel.getWidth(), viewModel.getHeight());
        });
    }


    public Void drawPositionedImage(int width, int height, PositionedImage positionedImage) {
        afterTranslate(() -> {
            pApplet.image(
                    positionedImage.getImage(),
                    positionedImage.getX(),
                    positionedImage.getY(),
                    width,
                    height);
        });

        return null;
    }
}
