package com.sath.tomcat;
import org.apache.coyote.http11.Http11NioProtocol;

import com.sath.test.CustomExecutor;

import java.util.concurrent.*;

public class CustomHttp11NioProtocol extends Http11NioProtocol {
	  ThreadPoolExecutor customExecutor ;
    public CustomHttp11NioProtocol() {
        super();
        System.out.println("CustomHttp Initialized!");
        customExecutor = new ThreadPoolExecutor(
                100,  // Core pool size
                300, // Max pool size
                60L, 
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000) ,
                new ThreadPoolExecutor.AbortPolicy()
        );

        
        
        System.out.println("Initialized!");
        System.out.println("Current Executor: " + getExecutor());
    
        	
        	
            this.setExecutor(customExecutor);
            this.setMinSpareThreads(20); 
            

            System.out.println(" Custom Executor Set Successfull");
            System.out.flush();
       
    }

    @Override
    public Executor getExecutor() {
        return customExecutor;  
    }

    @Override
    public void setExecutor(Executor executor) {
      
        super.setExecutor(executor);
        System.out.println("Executor set to: " + executor);
    }
  /*  static class CustomThreadFactory implements ThreadFactory {
        private int threadCount = 0;
        
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("Custom-Nio-Thread-" + (++threadCount));
            return thread;
        }
    }*/
  public void stop() throws Exception {
        System.out.println("🛑 Stopping Custom Protocol Handler...");
        shutdownExecutor();
        super.stop();
    }

    public void shutdownExecutor() {
        if (customExecutor != null && !customExecutor.isShutdown()) {
            System.out.println("Shutting Down Custom Executor...");
            customExecutor.shutdown();
        }
    }
}
