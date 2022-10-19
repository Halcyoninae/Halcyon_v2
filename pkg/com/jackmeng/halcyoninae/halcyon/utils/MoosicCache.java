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

import com.jackmeng.halcyoninae.cosmos.components.ErrorWindow;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.util.*;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class MoosicCache {
    // MoosicCache Config START
    public static final String NODE_ROOT = "moosic";
    public static final String NODE_USER_LIKED_TRACKS = "likedTracks";
    public static final String NODE_USER_SAVED_PLAYLISTS = "savedPlaylists";
    public static final String NODE_USER_EXCLUDED_TRACKS = "excludedTracks";
    public static String MOOSIC_DEFAULT_LOCALE = ProgramResourceManager.PROGRAM_RESOURCE_FOLDER
        + ProgramResourceManager.FILE_SLASH
        + ProgramResourceManager.RESOURCE_SUBFOLDERS[2] + ProgramResourceManager.FILE_SLASH + "moosic.halcyon";
    // MoosicCache Config END
    private final Object lock;
    private final Object lock2;
    private final Object lock3;
    public Cacher cacher;
    private List<String> excludedFiles, savedPlayLists;
    private Set<String> likedTracks;

    public MoosicCache() {
        lock = lock2 = lock3 = new Object();
        init();
    }

    public void init() {
        cacher = new Cacher(new File(MOOSIC_DEFAULT_LOCALE));
        /*
         * If the desired file for the configuration is not found, we will
         * target a default setting.
         *
         * Conditions:
         * 1. If the file doesn't exist
         * 2. If the size of the file is zero
         */
        if (!new File(MOOSIC_DEFAULT_LOCALE).exists() || new File(MOOSIC_DEFAULT_LOCALE).length() == 0
            || !new File(MOOSIC_DEFAULT_LOCALE).isFile()) {
            Debugger.warn("Incorrect user cache found! >,< Moosic resetting");
            Map<String, String> content = new WeakHashMap<>();
            content.put(NODE_USER_LIKED_TRACKS, "");
            content.put(NODE_USER_SAVED_PLAYLISTS, "");
            content.put(NODE_USER_EXCLUDED_TRACKS, "");
            try {
                cacher.build(NODE_ROOT, content);
            } catch (TransformerException | ParserConfigurationException e) {
                ExternalResource.dispatchLog(e);
                e.printStackTrace();
            }
            excludedFiles = new ArrayList<>();
            savedPlayLists = new ArrayList<>();
            likedTracks = new HashSet<>();
        } else {
            /*
             * We try to look up the data from the configuration file if the previous
             * conditions
             * are not met, meaning:
             * 1. File exists
             * 2. File size is not zero.
             * However error handling and how data is processed is handled by the user.
             */
            Debugger.info("Loading user cache...!! :D The moosic is on!");
            try {
                excludedFiles = cacher.getContent(NODE_USER_EXCLUDED_TRACKS)[0] != null ? new ArrayList<>(Arrays.asList(
                    cacher.getContent(NODE_USER_EXCLUDED_TRACKS)[0].split("\n"))) : new ArrayList<>();
                savedPlayLists = cacher.getContent(NODE_USER_SAVED_PLAYLISTS)[0] != null
                    ? new ArrayList<>(Arrays.asList(
                    cacher.getContent(NODE_USER_SAVED_PLAYLISTS)[0].split("\n")))
                    : new ArrayList<>();
                likedTracks = cacher.getContent(NODE_USER_LIKED_TRACKS)[0] != null ? new HashSet<>(Arrays.asList(
                    cacher.getContent(NODE_USER_LIKED_TRACKS)[0].split("\n"))) : new HashSet<>();
                Debugger.info("EF: " + excludedFiles, "SPL: " + savedPlayLists, "LT: " + likedTracks);
            } catch (Exception e) {
                ExternalResource.dispatchLog(e);
                e.printStackTrace();
                new ErrorWindow(e.getMessage()).run();
            }
        }
    }

    /**
     * @param path
     * @return boolean
     */
    public boolean isExcluded(String path) {
        return excludedFiles.contains(path);
    }

    public void pingLikedTracks() {
        synchronized (lock) {
            likedTracks = new HashSet<>(Arrays.asList(Global.ll.getFolder().getFilesAsStr()));
        }

    }

    public void pingSavedPlaylists() {
        synchronized (lock2) {
            savedPlayLists = new ArrayList<>(new HashSet<>(savedPlayLists));
        }
    }

    /**
     * @param exclude
     */
    public void pingExcludedTracks(String exclude) {
        if (!excludedFiles.contains(exclude)) {
            synchronized (lock3) {
                excludedFiles.add(exclude);
            }
        }
    }

    /**
     * @return A list of string
     */
    public List<String> getExcludedTracks() {
        return excludedFiles;
    }

    /**
     * @return A list of string
     */
    public List<String> getSavedPlaylists() {
        return savedPlayLists;
    }

    /**
     * @return A Set of String
     */
    public Set<String> getLikedTracks() {
        return likedTracks;
    }

    public void forceSave() {
        Map<String, String> content = new WeakHashMap<>();
        StringBuilder sb1 = new StringBuilder();
        excludedFiles.forEach(x -> sb1.append(x).append("\n"));
        content.put(NODE_USER_EXCLUDED_TRACKS, sb1.toString());
        StringBuilder sb2 = new StringBuilder();
        savedPlayLists.forEach(x -> sb2.append(x).append("\n"));
        content.put(NODE_USER_SAVED_PLAYLISTS, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        likedTracks.forEach(x -> sb3.append(x).append("\n"));
        content.put(NODE_USER_LIKED_TRACKS, sb3.toString());
        Debugger.info("Force Saving " + this.getClass().getSimpleName() + " > ", "ET: \n" + sb1,
            "SPL: \n" + sb2, "LT: \n" + sb3);
        try {
            cacher.build(NODE_ROOT, content);
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void forceSaveQuiet() {
        Map<String, String> content = new WeakHashMap<>();
        StringBuilder sb1 = new StringBuilder();
        for (String s : excludedFiles) {
            sb1.append(s).append("\n");
        }
        content.put(NODE_USER_EXCLUDED_TRACKS, sb1.toString());
        StringBuilder sb2 = new StringBuilder();
        for (String s : savedPlayLists) {
            sb2.append(s).append("\n");
        }
        content.put(NODE_USER_SAVED_PLAYLISTS, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        for (String s : likedTracks) {
            sb3.append(s).append("\n");
        }
        content.put(NODE_USER_LIKED_TRACKS, sb3.toString());
        try {
            cacher.build(NODE_ROOT, content);
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
