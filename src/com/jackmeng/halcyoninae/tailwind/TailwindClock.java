package com.jackmeng.halcyoninae.tailwind;

import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;

import java.util.concurrent.ExecutorService;

public class TailwindClock implements Runnable, TailwindListener.StatusUpdateListener {
    private ExecutorService worker;
    private final long time = 0L;
    private final boolean running = false;

    @Override
    public void run() {
    }


    /**
     * @param status
     */
    @Override
    public void statusUpdate(TailwindStatus status) {
        // TO BE IMPLEMENTED
    }

}
