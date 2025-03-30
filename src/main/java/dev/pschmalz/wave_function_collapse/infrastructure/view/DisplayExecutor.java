package dev.pschmalz.wave_function_collapse.infrastructure.view;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DisplayExecutor implements ExecutorService {
    private Queue<Runnable> commands = new ConcurrentLinkedQueue<>();
    private ExecutorService singleThread = Executors.newSingleThreadExecutor();

    private volatile boolean hasView = false;
    private Object obj = new Object();

    @Override
    public void execute(Runnable command) {
        synchronized (obj) {
            if(!hasView) {
                hasView = true;
                singleThread.execute(command);
                singleThread.shutdown();
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

    @Override
    public void shutdown() {
        singleThread.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return singleThread.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return singleThread.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return singleThread.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return singleThread.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return singleThread.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return singleThread.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return singleThread.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return singleThread.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return singleThread.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return singleThread.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return singleThread.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void close() {
        singleThread.close();
    }
}
