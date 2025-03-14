package com.sath.test;
import org.apache.catalina.Executor;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import java.util.concurrent.*;

public abstract  class CustomExecutor implements Executor,LifecycleListener {
    private  CustomThreadPoolExecutor executor;

    public CustomExecutor() {
        this.executor = new CustomThreadPoolExecutor(10, 50, 60, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
    }

    @Override
    public void execute(Runnable command) {
    	System.out.println("[CustomExecutor] Executing task");
        executor.execute(command);
    }

    @Override
    public void start() throws LifecycleException {
        System.out.println("Custom Executor Started.");
       
    }

    @Override
    public void stop() throws LifecycleException {
        executor.shutdown();
        System.out.println("Custom Executor Stopped.");
    }

    public boolean isDaemon() {
        return false;
    }

    @Override
    public String getName() {
        return "CustomExecutor";
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {}

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {}
}
	
