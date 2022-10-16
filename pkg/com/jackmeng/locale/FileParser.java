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


    /**
     * @param locale
     * @return boolean
     */
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
