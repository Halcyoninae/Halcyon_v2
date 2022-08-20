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

import javax.swing.*;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.TabTree;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
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
     *
     * @param runnable The runnable to be launched
     */
    public static void async(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void sort(TabTree tree) {
        File[] f = new File(tree.getPath()).listFiles();
        Arrays.sort(f, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }


    /**
     * @param runnable
     */
    public static void asyncSwingUtil(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }

    /**
     * Launches a Runnable in a precatched
     * Exception handler.
     *
     * @param runnable The task to run safely on.
     */
    public static void safeLog(Runnable runnable, boolean isAsync) {
        try {
            if (isAsync)
                async(runnable);
            else
                runnable.run();
        } catch (Exception e) {
            ExternalResource.dispatchLog(e);
        }
    }


    /**
     * @param run
     */
    public static void threadedRun(Runnable run) {
        ExecutorService es = Executors.newWorkStealingPool();
        es.submit(run);
    }
}
