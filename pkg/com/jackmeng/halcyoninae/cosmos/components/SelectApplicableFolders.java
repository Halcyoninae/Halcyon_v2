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

package com.jackmeng.halcyoninae.cosmos.components;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import java.io.File;

/**
 * This dialog class constructs a simple
 * JFileChooser that alerts any listeners of a
 * file being selected.
 *
 * @author Jack Meng
 * @see java.lang.Runnable
 * @see javax.swing.JFileChooser
 * @since 3.0
 */
public class SelectApplicableFolders extends JFileChooser implements Runnable {

    private transient FolderSelectedListener fs = null;

    /**
     * Constructs a new a dialog
     * <p>
     * This new dialog is not visible until {@link #run()} is called.
     * <p>
     * Note that a listener is considered a dead listener
     * if the listener is attempted to be set after the dialog
     * has been run and the user has chosen the file without any
     * attached listener at that time.
     */
    public SelectApplicableFolders() {
        super();
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
        setApproveButtonText("Add Folder");
        setApproveButtonToolTipText("Add this folder as a playlist to play from");
        setDialogTitle("Halcyon ~ Add Playlist");
        setFileHidingEnabled(false);
        setMultiSelectionEnabled(false);
        FileSystemView fsv = FileSystemView.getFileSystemView();
        setCurrentDirectory(fsv.getHomeDirectory());
        setFileView(new FileView() {
            @Override
            public Icon getIcon(File f) {
                return FileSystemView.getFileSystemView().getSystemIcon(f);
            }
        });
    }

    /**
     * This sets the current Dialog's folder
     * listener to the one in the parameter.
     *
     * @param fs The valid listener locale
     */
    public void setFolderSelectedListener(FolderSelectedListener fs) {
        this.fs = fs;
    }

    /**
     * Runs the dialog with the attached listener if there is
     * If no dialog is set (== null) no event will be dispatched.
     *
     * @see java.lang.Runnable
     */
    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            int returnVal = showOpenDialog(new FSVDefault());
            if (returnVal == JFileChooser.APPROVE_OPTION && fs != null) {
                fs.folderSelected(getSelectedFile().getAbsolutePath());
            }
        });
    }

    /**
     * This interface represents the standard listener for
     * when a file is selected from the dialog.
     *
     * @author Jack Meng
     * @since 3.0
     */
    public interface FolderSelectedListener {
        /**
         * A listener for when a folder is selected
         *
         * @param folder The folder that was dispatched by the event
         */
        void folderSelected(String folder);
    }
}
