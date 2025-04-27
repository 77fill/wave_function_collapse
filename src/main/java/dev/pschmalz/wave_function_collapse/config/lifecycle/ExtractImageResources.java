package dev.pschmalz.wave_function_collapse.config.lifecycle;

import dev.pschmalz.wave_function_collapse.usecase.InitTempDirectory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ExtractImageResources implements SmartLifecycle {
    public static final int PHASE = 1000;

    InitTempDirectory initTempDirectory;

    @NonFinal
    volatile boolean running = false;

    @Override
    public void start() {
        running = true;
        initTempDirectory.await();
        running = false;
    }

    @Override
    public void stop() {
        initTempDirectory.cancel();
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return PHASE;
    }
}
