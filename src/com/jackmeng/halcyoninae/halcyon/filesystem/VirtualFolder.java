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

package com.jackmeng.halcyoninae.halcyon.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that represents a virtual folder,
 * this virtual folder does not exist on the user's
 * actual hard drive and is used to represent a virtual
 * folder that may be used to hold files from different
 * stuffs.
 * <p>
 * The entirety of the methods from FolderInfo are overriden
 * in order to completely encompass a "virtual" folder
 *
 * @author Jack Meng
 * @since 3.1
 */
public class VirtualFolder extends PhysicalFolder {
    private final List<File> list;

    /**
     * A VirtualFolder takes a varargs of file objects
     * in order to represent the folder with files.
     * <p>
     * The absolute path of the folder is replaced
     * by a generic name.
     *
     * @param name  The name of the virtual folder.
     * @param files The files in the virtual folder.
     */
    public VirtualFolder(String name, File... files) {
        super(name);
        this.list = new ArrayList<>(Arrays.asList(files));
    }

    /**
     * @return String[] A list of files of their absolute paths in string form
     */
    @Override
    public String[] getFilesAsStr() {
        String[] str = new String[getFiles().length];
        for (int i = 0; i < str.length; i++) {
            str[i] = getFiles()[i].getAbsolutePath();
        }
        return str;
    }

    /**
     * @return File[] A list of files of their File object instances (created on
     * call)
     */
    @Override
    public File[] getFiles() {
        return list.toArray(new java.io.File[0]);
    }

    /**
     * Applies a check against the list of avaliable files.
     *
     * @param rules A set of rules to be used to compare the ending of the file
     *              (endsWith)
     * @return String[] A list of file of their absolute paths in string form
     */
    @Override
    public String[] getFilesAsStr(String... rules) {
        List<String> buff = new ArrayList<>();
        for (File f : list) {
            for (String rule : rules) {
                if (f.getAbsolutePath().endsWith(rule)) {
                    buff.add(f.getName());
                }
            }
        }
        return buff.toArray(new String[0]);
    }

    /**
     * Applies a check against the list of avaliable files.
     * <p>
     * This uses the method {@link #getFilesAsStr(String...)} method
     * for validation.
     *
     * @param rules A set of rules to be used to comapre the ending of the file
     *              (endsWith)
     * @return File[] A list of file of their respective absolute paths
     */
    @Override
    public File[] getFiles(String... rules) {
        List<File> files = new ArrayList<>();
        for (File str : getFiles()) {
            for (String r : rules) {
                if (str.getAbsolutePath().endsWith(r)) {
                    files.add(str);
                }
            }
        }
        return files.toArray(new File[0]);
    }

    /**
     * Insert a file into this
     * virtual folder instance.
     * <p>
     * This method does not handle if
     * this file instance exists or not.
     * <p>
     * The implementation must handle this by
     * themselves.
     *
     * @param f A file to insert.
     */
    public synchronized void addFile(java.io.File f) {
        list.add(f);
    }

    /**
     * Returns the absolute list.
     *
     * @return A List
     */
    public List<File> getAsListFiles() {
        return list;
    }

    /**
     * Removes a file from the
     * absolute list.
     *
     * @param f The file instance to remove.
     * @return (true | | false) if the action was a success
     */
    public synchronized boolean removeFile(File f) {
        return list.remove(f);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "[ VIRTUAL FOLDER @ " + getName() + "-" + list.toString() + " ]";
    }
}
