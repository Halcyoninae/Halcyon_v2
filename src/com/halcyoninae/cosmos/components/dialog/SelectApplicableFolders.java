/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
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

package com.halcyoninae.cosmos.components.dialog;

import com.halcyoninae.cosmos.components.inheritable.FSVDefault;

import javax.swing.*;

/**
 * This dialog class constructs a simple
 * JFileChooser that alerts any listeners of a
 * file being selected.
 *
 * @author Jack Meng
 * @since 3.0
 * @see java.lang.Runnable
 * @see javax.swing.JFileChooser
 */
public class SelectApplicableFolders extends JFileChooser implements Runnable {

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

  private transient FolderSelectedListener fs = null;

  /**
   * Constructs a new a dialog
   *
   * This new dialog is not visible until {@link #run()} is called.
   *
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
}
