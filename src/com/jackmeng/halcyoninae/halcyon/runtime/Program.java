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

package com.jackmeng.halcyoninae.halcyon.runtime;

import com.jackmeng.halcyoninae.halcyon.cacher.MoosicCache;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.filesystem.PhysicalFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Provides a concurrent logging system for the program
 * instead of using Debugger, which is meant for debugging during
 * initial development.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class Program {
    public static MoosicCache cacher = new MoosicCache();
    private static ExecutorService executorService;

    /**
     * Different from Debugger's log method, this method is meant for
     * printing out messages to the console, however can only
     * print out a string.
     * <p>
     * Asynchronous println t the console.
     *
     * @param e A string
     */
    private static void println(String e) {
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool(
                    r -> {
                        Thread t = new Thread(r);
                        t.setDaemon(true);
                        return t;
                    });
            executorService.submit(
                    new Runnable() {
                        @Override
                        public synchronized void run() {
                            while (true) {
                                try {
                                    wait();
                                    System.err.println(e);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }

    /**
     * Writes a dump file to the bin folder.
     * <p>
     * This is not a serialization byte stream!!!
     * <p>
     * This method is typically used for debugging and coverage
     * telemetry stuffs (idk :/).
     *
     * @param content The Objects to dump or content.
     */
    public static void createDump(Object... content) {
        StringBuilder sb = new StringBuilder();
        for (Object o : content) {
            sb.append(o.toString());
        }
        String s = sb.toString();
        File f = new File(
                ProgramResourceManager.PROGRAM_RESOURCE_FOLDER + "/bin/dump_" + System.currentTimeMillis() + "_.halcyon");
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void forceSaveUserConf() {
        cacher.forceSave();
    }

    /**
     * @return File[] Returns all of the liked tracks.
     */
    public static File[] fetchLikedTracks() {
        if (cacher.getLikedTracks().size() == 0) {
            return new File[0];
        } else {
            Set<File> list = new HashSet<>();
            for (String s : cacher.getLikedTracks()) {
                File f = new File(s);
                if (f.isFile() && f.exists()) {
                    list.add(f);
                }
            }
            return list.toArray(new File[0]);
        }
    }

    /**
     * @return FolderInfo[] Returns all concurrently stored playlists in the running
     * instance.
     */
    public static PhysicalFolder[] fetchSavedPlayLists() {
        if (cacher.getSavedPlaylists().size() == 0 || cacher.getSavedPlaylists() == null) {
            Debugger.warn("SPL_Condition: " + 0);
            return new PhysicalFolder[0];
        } else {
            List<PhysicalFolder> list = new ArrayList<>();
            for (String s : cacher.getSavedPlaylists()) {
                File f = new File(s);
                if (f.isDirectory() && f.exists()) {
                    list.add(new PhysicalFolder(f.getAbsolutePath()));
                }
            }
            return list.toArray(new PhysicalFolder[0]);
        }
    }

    /**
     * This main function runs the daemon for system logging
     * <p>
     * This is provided under an executor service that runs
     * async to the main thread in order to log everything made by the
     * the program.
     * <p>
     * This async mechanism is called natively.
     *
     * @param args Initial items to log
     */
    public static void main(String... args) {
        for (String arg : args) {
            println(arg);
        }
    }
}
