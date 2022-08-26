package com.jackmeng.halcyoninae.tailwind;

import java.util.concurrent.ExecutorService;

import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

public class TailwindClock implements Runnable, TailwindListener.StatusUpdateListener {
    private ExecutorService worker;
    private long time = 0L;
    private boolean running = false;

    @Override
    public void run() {
    }

    @Override
    public void statusUpdate(TailwindStatus status) {
        // TO BE IMPLEMENTED
    }

}
