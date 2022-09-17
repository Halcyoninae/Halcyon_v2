/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
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
