package dev.pschmalz.wave_function_collapse.infrastructure.view.scenes.images_grid;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class PAppletDecorator {
    private final PApplet pApplet;
    private PVector upperLeft;
    private int width, height;

    public PAppletDecorator(PApplet pApplet, PVector upperLeft, int width, int height) {
        this.pApplet = pApplet;
        this.upperLeft = upperLeft.copy();
        this.width = width;
        this.height = height;
    }

    private void afterTranslate(Runnable drawThis) {
        pApplet.pushMatrix();
        pApplet.translate(upperLeft.x, upperLeft.y);

        drawThis.run();

        pApplet.popMatrix();
    }

    public void background(int background) {
        afterTranslate(() -> {
            pApplet.rectMode(PConstants.CORNER);
            pApplet.fill(background);
            pApplet.rect(0, 0, width, height);
        });
    }

    public void drawPositionedImage(PositionedImage positionedImage) {
        afterTranslate(() -> {
            pApplet.image(
                    positionedImage.getImage(),
                    positionedImage.getX(),
                    positionedImage.getY(),
                    positionedImage.getWidth(),
                    positionedImage.getHeight());
        });
    }
}
