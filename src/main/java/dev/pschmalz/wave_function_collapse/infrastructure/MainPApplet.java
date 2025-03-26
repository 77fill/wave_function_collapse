package dev.pschmalz.wave_function_collapse.infrastructure;

import dev.pschmalz.wave_function_collapse.usecase.ChooseTileImages_CreateTiles;
import dev.pschmalz.wave_function_collapse.usecase.LoadResources_IntoTempDirectory;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.View;
import processing.core.PApplet;

import java.util.Queue;

public class MainPApplet extends PApplet implements View {
    private Queue<Runnable> eventQueue;
    private LoadResources_IntoTempDirectory loadResources_intoTempDirectory;
    private ChooseTileImages_CreateTiles chooseTileImages_createTiles;


    public MainPApplet(Queue<Runnable> eventQueue, LoadResources_IntoTempDirectory loadResources_intoTempDirectory, ChooseTileImages_CreateTiles chooseTileImages_createTiles) {
        this.eventQueue = eventQueue;
        this.loadResources_intoTempDirectory = loadResources_intoTempDirectory;
        this.chooseTileImages_createTiles = chooseTileImages_createTiles;
    }

    @Override
    public void settings() {
        size(1000,1000);
        noSmooth();
    }

    @Override
    public void setup() {
        loadResources_intoTempDirectory.execute(this);
    }

    @Override
    public void draw() {
        background(0);

        eventQueue.forEach(Runnable::run);
    }

    @Override
    public void tempDirectoryLoaded() {

    }

    @Override
    public void tilesLoaded() {

    }
}
