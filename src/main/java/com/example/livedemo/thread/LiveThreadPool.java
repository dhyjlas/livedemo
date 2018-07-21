package com.example.livedemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class LiveThreadPool {
    private static final Logger log = LoggerFactory.getLogger(LiveThreadPool.class);

    private static ExecutorService executor = Executors.newFixedThreadPool(5);
    private static LiveThreadPool instance = new LiveThreadPool();

    private LiveThreadPool(){}

    public static LiveThreadPool getInstance(){
        return instance;
    }

    public void execute(Runnable runnable){
        log.info("提交任务{}", runnable);
        executor.execute(runnable);
    }
}
