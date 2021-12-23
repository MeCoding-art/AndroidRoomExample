package com.machine.androidroomexample.utilities;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    //for Singleton instance
    private static final Object LOCK = new Object();
    private static  AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread){
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutors());
            }
        }

        return sInstance;
    }

    public Executor diskIO(){
        return diskIO;
    }

    public Executor mainThread(){
        return mainThread;
    }

    public Executor networkIO(){
        return networkIO;
    }

    private static class MainThreadExecutors implements Executor{

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

}
