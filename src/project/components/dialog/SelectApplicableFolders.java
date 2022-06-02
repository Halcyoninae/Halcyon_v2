package project.components.dialog;

import javax.swing.*;

import project.components.inheritabledialog.FSVDefault;

public class SelectApplicableFolders extends JFileChooser implements Runnable {
  public interface FolderSelectedListener {
    public void folderSelected(String folder);
  }

  private transient FolderSelectedListener fs = null;

  public SelectApplicableFolders() {
    super();
    setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    setAcceptAllFileFilterUsed(false);
  }

  public void setFolderSelectedListener(FolderSelectedListener fs) {
    this.fs = fs;
  }

  @Override
  public void run() {
    int returnVal = showOpenDialog(new FSVDefault());
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      if(fs != null) {
        fs.folderSelected(getSelectedFile().getAbsolutePath());
      }
    }
  }
}
