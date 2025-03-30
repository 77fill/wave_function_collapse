package dev.pschmalz.wave_function_collapse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import processing.core.PApplet;

@Component
public class ViewStartStop implements SmartLifecycle {
    @Autowired
    private PApplet pApplet;
    private volatile boolean running = false;

    @Override
    @Async("display")
    public void start() {
        running = true;
        PApplet.runSketch(new String[] {"", ""}, pApplet);
    }

    @Override
    public void stop() {
        running = false;
        pApplet.stop();
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
