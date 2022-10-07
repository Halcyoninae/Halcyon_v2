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

package com.jackmeng.locale;

import java.util.concurrent.Callable;

/**
 * A Job is a master concurrency model, which each can hold
 * a finite amount of workers all helping to achieve a TASK.
 * <p>
 * A job is similar to standard Threads or CompletableFutures,
 * but are more oriented towards SWT or single threaded applications.
 * It also allows for much control over the content being run and updated.
 * <p>
 * In regards to the Halcyon Music Player, an ImageJob is used to
 * modify images without blocking the SWT and EventQueue. The modified
 * image will be automatically referenced once they are done.
 *
 * @author Jack Meng
 * @since 3.3
 */
public interface Job extends Runnable {
    /**
     * Quit the current job being run.
     * The current job's content and runnable is
     * completely nulled and is not kept.
     * <p>
     * HOWEVER: The above retention method is
     * up to the programmer's own intentions.
     */
    void quit();

    /**
     * If a retention policy is used,
     * the user could restart the last job; however,
     * an implementation shall not provide a retention policy
     * in which a restart could be called for EVERY last
     * job reference. This retention method may result
     * in severe memory consumptions and poor performance,
     * which a Job is not supposed to be in most cases.
     */
    void restartLabJob();

    /**
     * If the current job has a task to run, this method
     * will call a "pause" on the task but not quit
     * the job. This method is not necessary to be
     * implemented and most likely with internal Java
     * Concurrency might result in the concurrency
     * not pausing and simply being ignored.
     */
    void failCurrentTask();

    /**
     * Restart the CURRENT task. The current job
     * reference is dereferenced and cannot be called
     * by restartLabJob to retart the task. This in turn
     * results in less memory being consumed and needed to
     * be stored.
     */
    void restartJob();

    /**
     * This method does not gurantee that the job will
     * immediately do any of the following: quit the current task, add this task
     * to the queue, start this task if there are none running, and/or quit
     * the last job and pick up this.
     * <p>
     * This is used mainly to tell the job queue that a new task
     * may be needed to be added but no gurantees shall be made
     * in order to reduce the current task experiencing any overhead.
     *
     * @param task An anonymous class or function can be used here to facilitate the
     *             quick
     *             job insertion.
     */
    void setTask(Runnable task);

    /**
     * Similar to {@link #setTask(Runnable)} but the user may wish to
     * use a standard Callable implementation.
     * <p>
     * Once again, this method makes no gurantees of the following:
     * quiting the current task, adding this task to the queue, start this
     * task if there are none running, and/or quit the current task and readily
     * pick up this task.
     *
     * @param task A Callable implementation (wildcard)
     */
    void setTask(Callable<?> task);
    // BEGIN INTERNAL METHODS
    // Internal methods usually start with an underscore
    // Internal methods are called by forwarding methods to function as the "main"
    // part of the class, etc.

    /**
     * Most likely should provide the main implementation and checks for
     * {@link #failCurrentTask()}
     * and will return true or false based on the status.
     *
     * @return (true | | false) A true is returned if the task was able to be failed
     * and has been failed. A false is returned if the task is unable to
     * be checked, failed, and/or be present
     */
    boolean __current_task_fail();

    /**
     * Most likely used by {@link #restartLabJob()} and {@link #setTask(Callable)} +
     * {@link #setTask(Runnable)} to check
     * if the current implementation allows for reference retention of the tasks
     * that have been ran previously in order
     * to quickly pick them up if the implementation calls for such a method.
     * <p>
     * This method must be concrete and provide exact readings on whether the job
     * can do so.
     *
     * @return (true | | false) Whether the job supports retention of task
     * references.
     */
    boolean __is_retainable();

    /**
     * This method should not be used externally as it can call for the current job
     * (based on standard
     * implementation) to start a new worker while keeping the old worker running,
     * resulting in the job
     * owning 2 workers which can lead to concurrency issues in which the
     * implementation
     * itself must check for, which in turn causes overhead.
     * <p>
     * This method is primarily used with {@link #setTask(Callable)},
     * {@link #setTask(Runnable)}, {@link #restartJob()}, {@link #quit()}
     * to force a job from the queue to be picked up. However once again, due to the
     * concurrency model, no such gurantees are made in any
     * way.
     * <p>
     * Although no gurantee can be made, one can provide native implementations to
     * allow for much more specific controls over the flow.
     *
     * @param default_fail_runner An anonymous that can be called if the task was
     *                            failed either due to an exception, or a premature
     *                            end
     * @param original_task       The task to run which is the main task that is
     *                            asked for
     */
    void __assign_job_(Runnable default_fail_runner, Runnable original_task);

    // END INTERNAL METHODS
    @Override
    default void run() {
        // DO NOTHING FOR AN EMPTY JOB ON DEFAULT NULL RUNNER
    }
}
