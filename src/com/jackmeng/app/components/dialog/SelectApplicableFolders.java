package com.jackmeng.app.components.dialog;

import javax.swing.*;

import com.jackmeng.app.components.inheritabledialog.FSVDefault;

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
    public void folderSelected(String folder);
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
    int returnVal = showOpenDialog(new FSVDefault());
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      if (fs != null) {
        fs.folderSelected(getSelectedFile().getAbsolutePath());
      }
    }
  }
}
