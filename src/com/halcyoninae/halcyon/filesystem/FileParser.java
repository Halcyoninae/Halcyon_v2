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

package com.halcyoninae.halcyon.filesystem;

import com.halcyoninae.halcyon.runtime.TextBOM;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A simply utility that helps
 * with parsing information regarding files or a folder.
 *
 * @author Jack Meng
 * @see java.io.File
 * @since 3.0
 */
public final class FileParser {

    /**
     * Irrelevant constructor :(
     */
    private FileParser() {
    }

    /**
     * This method parses files with a certain extension.
     *
     * @param folder The folder to search for, however this parameter must be
     *               guaranteed to be a folder and must exist and accessible.
     * @param rules  An array of extensions to search for and compare to.
     * @return An array of File objects that will be returned of all of the files
     * that match the rules for the specified file extensions.
     */
    public static File[] parseOnlyAudioFiles(File folder, String[] rules) {
        File[] files = folder.listFiles();
        ArrayList<File> audioFiles = new ArrayList<>();
        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                for (String rule : rules) {
                    if (f.getAbsolutePath().endsWith("." + rule)) {
                        audioFiles.add(f);
                    }
                }
            }
        }
        return audioFiles.toArray(new java.io.File[0]);
    }

    /**
     * Returns if a folder contains in any way a file with the specified extension.
     *
     * @param folder The folder to search for, however this parameter must be
     *               guaranteed to be a folder and must exist and accessible.
     * @param rules  An array of extensions to search for and compare to.
     * @return True if the folder contains a file with the specified extension,
     * false otherwise.
     */
    public static boolean contains(File folder, String[] rules) {
        File[] files = folder.listFiles();
        assert files != null;
        for (File f : files) {
            if (f.isFile()) {
                for (String rule : rules) {
                    if (f.getAbsolutePath().endsWith("." + rule)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method parses a folder's name
     *
     * @param f The folder to parse
     * @return The name of the folder
     * <p>
     * Very useless method
     */
    public static String folderName(String f) {
        return new File(f).getName();
    }

    /**
     * This method returns a string array of file names from the
     * given varargs of files to parse.
     *
     * @param files A varargs of files to parse
     * @return A string array of file names
     */
    public static String[] parseFileNames(File... files) {
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            if (files[i] != null && files[i].isFile()) {
                fileNames[i] = files[i].getName();
            }
        }
        return fileNames;
    }

    /**
     * Checks if the folder is empty (void of any files)
     *
     * @param folder The folder to check
     * @return True if the folder is empty, false otherwise
     */
    public static boolean isEmptyFolder(File folder) {
        File[] files = folder.listFiles();
        assert files != null;
        return files.length == 0;
    }

    public static boolean checkDirExistence(String locale) {
        File f = new File(locale);
        return f.isDirectory() && f.exists();
    }

    /**
     * @param os
     * @param encoding
     * @throws IOException
     */
    public static void writeBOM(OutputStream os, String encoding) throws IOException {
        os.write(encoding.equals("UTF-8") ? TextBOM.UTF8_BOM
                : (encoding.equals("UTF-16LE") ? TextBOM.UTF16_LE_BOM : TextBOM.UTF16_BE_BOM));
    }
}
