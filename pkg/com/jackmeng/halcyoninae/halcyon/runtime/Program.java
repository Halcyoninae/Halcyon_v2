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

package com.jackmeng.halcyoninae.halcyon.runtime;

import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.MoosicCache;
import com.jackmeng.halcyoninae.halcyon.utils.ProgramResourceManager;
import com.jackmeng.locale.PhysicalFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    }

    /**
     * Localization Method
     */
    public static void localize() {
        new File("/halcyon/").mkdir();
    }
}
