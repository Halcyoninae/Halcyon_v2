/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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
     * @param run A runnable or an anonymous class can be used for this implementation
     * @author Jack Meng
     * @since 3.2
     */
    public static void threadedRun(Runnable run) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(run);
    }
}
