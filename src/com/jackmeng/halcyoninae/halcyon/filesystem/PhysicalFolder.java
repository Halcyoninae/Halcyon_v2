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

package com.jackmeng.halcyoninae.halcyon.filesystem;

import java.io.File;

/**
 * A simple class that inits and holds
 * a folder information.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class PhysicalFolder {
    private String absPath = ".";

    /**
     * Constructs the folder-info object instance with
     * the specified path.
     * <p>
     * Note: this path does not assert that the entered instance is
     * a folder that exists or the file system has access/permission to.
     *
     * @param absolutePath The path to construct the folder-info object with.
     */
    public PhysicalFolder(String absolutePath) {
        this.absPath = absolutePath;
    }

    /**
     * Returns the absolute path of the folder-info object.
     *
     * @return The absolute path of the folder-info object.
     */
    public String getAbsolutePath() {
        return absPath;
    }

    /**
     * Returns the Files (not sub-folders) in the folder-info object
     * or the folder as the file's names.
     *
     * @return A string array
     */
    public String[] getFilesAsStr() {
        File[] f = new File(absPath).listFiles();
        assert f != null;
        String[] s = new String[f.length];
        for (int i = 0; i < f.length; i++) {
            s[i] = f[i].getName();
        }
        return s;
    }

    /**
     * Returns all of the files within the folder
     *
     * @return File array
     */
    public File[] getFiles() {
        return new File(absPath).listFiles();
    }

    /**
     * Get this folder's name
     *
     * @return A String
     */
    public String getName() {
        return new File(absPath).getName();
    }

    /**
     * Returns an array of String in which each String
     * represents a file inside the folder (absolute path).
     *
     * @param rules An array of extensions to search for and compare to.
     * @return An array of String
     */
    public String[] getFilesAsStr(String... rules) {
        File[] f = new File(absPath).listFiles();
        assert f != null;
        String[] s = new String[f.length];
        for (int i = 0; i < f.length; i++) {
            if (f[i].isFile()) {
                String curr = f[i].getAbsolutePath();
                for (String r : rules) {
                    if (curr.endsWith(r)) {
                        s[i] = curr;
                        break;
                    }
                }
            }
        }
        return s;
    }

    /**
     * @param rules An array of extensions to search for and compare to.
     * @return An array of Files with the specified extension.
     */
    public File[] getFiles(String... rules) {
        File[] f = new File(absPath).listFiles();
        assert f != null;
        File[] s = new File[f.length];
        for (int i = 0; i < f.length; i++) {
            if (f[i].isFile()) {
                String curr = f[i].getAbsolutePath();
                for (String r : rules) {
                    if (curr.endsWith(r)) {
                        s[i] = f[i];
                        break;
                    }
                }
            }
        }
        return s;
    }

    /**
     * Represents the folder-info object as a string.
     */
    public String toString() {
        return absPath + "[" + java.util.Arrays.toString(getFilesAsStr()) + "]";
    }
}
