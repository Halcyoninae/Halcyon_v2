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

package com.jackmeng.halcyoninae.halcyon.utils;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.TabTree;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class provides a globalized way
 * for certain tasks to be executed
 * without making the code too complex.
 *
 * @author Jack Meng
 * @author 3.1
 */
public final class Wrapper {
    /**
     * Launches a runnable in an async pool.
     * <p>
     * This method should only be used for non-infinite
     * runnables, and should only be used for one time
     * or simple loops.
     * <p>
     * If a complex task is needed, especially with GUI,
     * a Job implementation should be used.
     *
     * @param runnable The runnable to be launched
     * @author Jack Meng
     * @see com.jackmeng.halcyoninae.halcyon.worker.Job
     * @since 3.1
     */
    public static void async(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * Attempts to sort a standard TabTree implementation.
     * A TabTree is a master interface that holds information regarding
     * a single GUI playlist.
     *
     * @param tree A TabTree implementation with valid File listings.
     * @author Jack Meng
     * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.TabTree
     * @since 3.3
     */
    public static void sort(TabTree tree) {
        File[] f = new File(tree.getPath()).listFiles();
        assert f != null;
        Arrays.sort(f, Comparator.comparing(File::getName));
    }

    /**
     * Uses an ExecutorService managed Threadpool to run a
     * particular task. See the documentation of {@link #async(Runnable)}
     * for the exact documentations as the overall usage are the same.
     *
     * @param run A runnable or an anonymous class can be used for this
     *            implementation
     * @author Jack Meng
     * @since 3.2
     */
    public static void threadedRun(Runnable run) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(run);
    }
}
