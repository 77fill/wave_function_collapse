package dev.pschmalz.wave_function_collapse.config.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import processing.core.PApplet;

@Component
public class ViewStartStop implements SmartLifecycle {
    @Autowired
    private PApplet pApplet;
    private volatile boolean running = false;

    @Override
    public void start() {
        running = true;
        PApplet.runSketch(new String[] {"", ""}, pApplet);
    }

    @Override
    public void stop() {
        pApplet.exit();
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
