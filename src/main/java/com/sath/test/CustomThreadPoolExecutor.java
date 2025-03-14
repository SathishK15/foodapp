package com.sath.test;

import java.util.concurrent.*;
import java.util.logging.Logger;

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

	
	    public CustomThreadPoolExecutor(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
	        super(corePoolSize, maxPoolSize, keepAliveTime, unit, workQueue);
	        System.out.println("Custom ThreadPoolExecutor Initialized");
	    }

	    @Override
	    protected void beforeExecute(Thread t, Runnable r) {
	        System.out.println("Before Execution: Thread " + t.getName() + " is starting a task.");
	        super.beforeExecute(t, r);
	    }

	    @Override
	    protected void afterExecute(Runnable r, Throwable t) {
	        System.out.println("After Execution: Task completed in thread " + Thread.currentThread().getName());
	        super.afterExecute(r, t);
	    }

	    @Override
	    protected void terminated() {
	        System.out.println("ThreadPool is shutting down.");
	        super.terminated();
	    }
	    
	}

