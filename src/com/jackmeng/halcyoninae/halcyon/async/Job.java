/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly..
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.halcyoninae.halcyon.async;

import com.jackmeng.halcyoninae.halcyon.debug.Debugger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Job implements JobListener {
    private ExecutorService worker;
    private List<JobListener> listeners;
    private long terminationHalt, waitingTime;
    private boolean isSuccess, isFail;
    private Callable<Integer> task;


    /**
     * @param state
     */
    private void __dispatch_status(int state) {
        listeners.forEach(x -> x.jobTask(state));
    }

    public Job() {
        worker = Executors.newSingleThreadExecutor();
        terminationHalt = 500L;
        waitingTime = 200L;
        isSuccess = false;
        isFail = false;
        listeners = new ArrayList<>();
        listeners.add(this);
        task = () -> 0;
    }


    /**
     * @param listeners
     */
    public void addJobStateListener(JobListener... listeners) {
        for (JobListener e : listeners) {
            this.listeners.add(e);
        }
    }


    /**
     * @return boolean
     */
    public boolean isSuccessExit() {
        return isSuccess && !isFail;
    }


    /**
     * @return boolean
     */
    public boolean isFailedExit() {
        return !isSuccess && isFail;
    }


    /**
     * @return boolean
     */
    public boolean isExited() {
        return isSuccess || isFail;
    }


    /**
     * @return boolean
     */
    public boolean isRunning() {
        return task != null && (!worker.isShutdown() || !worker.isTerminated());
    }


    /**
     * @return long
     */
    public long getWaitingTime() {
        return waitingTime;
    }


    /**
     * @return long
     */
    public long getTerminationTime() {
        return terminationHalt;
    }


    /**
     * @param l
     */
    public void setWaitingTime(long l) {
        this.waitingTime = l;
    }


    /**
     * @param l
     */
    public void setTerminationTime(long l) {
        this.terminationHalt = l;
    }


    /**
     * @param task
     */
    public void runTaskSafe(Callable<Integer> task) {
        if (!worker.isShutdown()) {
            cancel();
        }
        this.task = task;
        Future<Integer> conc = worker.submit(task);
        while (!conc.isDone()) {
            try {
                __dispatch_status(conc.get());
                break;
            } catch (InterruptedException | ExecutionException e) {
                Debugger.warn(e.getMessage());
            }
            try {
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                Debugger.warn(e.getMessage());
            }
        }
        this.task = null;
        worker.shutdownNow();
    }

    public void cancel() {
        worker.shutdown();
        try {
            if (!worker.awaitTermination(terminationHalt, TimeUnit.MILLISECONDS)) {
                worker.shutdownNow();
            }
        } catch (InterruptedException e) {
            worker.shutdownNow();
        }
    }


    /**
     * @param lastStatus
     */
    @Override
    public void jobTask(int lastStatus) {
        Debugger.warn("JOB > " + lastStatus + " Code Got for the last current task!!");
        isSuccess = lastStatus == 0;
        isFail = lastStatus == -1 || lastStatus == 1;
    }

}
