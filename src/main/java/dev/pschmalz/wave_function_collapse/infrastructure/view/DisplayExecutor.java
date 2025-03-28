package dev.pschmalz.wave_function_collapse.infrastructure.view;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class DisplayExecutor implements Executor {
    private Queue<Runnable> commands = new ConcurrentLinkedQueue<>();
    private Executor singleThread = Executors.newSingleThreadExecutor();

    private volatile boolean hasView = false;
    private Object obj = new Object();

    @Override
    public void execute(Runnable command) {
        synchronized (obj) {
            if(!hasView) {
                hasView = true;
                singleThread.execute(command);
            }
        }

        commands.add(command);
    }

    public void runCommands() {
        Runnable command = null;
        while ((command = commands.poll()) != null) {
            command.run();
        }
    }
}
