package dev.pschmalz.wave_function_collapse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.annotation.Async;
import processing.core.PApplet;

public class ViewStartStop implements SmartLifecycle {
    @Autowired
    PApplet pApplet;

    @Override
    @Async("display")
    public void start() {
        PApplet.runSketch(new String[] {"", ""}, pApplet);
    }

    @Override
    public void stop() {
        pApplet.stop();
    }

    @Override
    public boolean isRunning() {
        return !pApplet.finished;
    }
}
