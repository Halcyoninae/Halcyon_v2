package com.jackmeng.app.components.bottompane;

import javax.swing.*;

import com.jackmeng.app.utils.FolderInfo;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the Tabs in the BottomPane.
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class BottomPane extends JTabbedPane {
  private List<FileList> tabs;
  /**
   * Represents an absolute list of folders
   * that have been selected by the user.
   */
  private ArrayList<String> foldersAbsolute;

  public BottomPane() {
    super();
    foldersAbsolute = new ArrayList<>();

    setPreferredSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    this.tabs = new ArrayList<>();
    setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
  }

  public List<FileList> getTabs() {
    return tabs;
  }

  public FileList findByTree(JTree tree) {
    for (FileList tab : tabs) {
      if (tab.getTree().equals(tree)) {
        return tab;
      }
    }
    return null;
  }

  public boolean containsFolder(String folderAbsoluteStr) {
    return foldersAbsolute.contains(folderAbsoluteStr);
  }

  public List<String> getStrTabs() {
    return foldersAbsolute;
  }

  public void pokeNewFileListTab(String folder) {
    FileList list = new FileList(new FolderInfo(folder));
    addTab(new File(folder).getName(), Global.rd.getFromAsImageIcon(Manager.FILEVIEW_DEFAULT_FOLDER_ICON), list,
        "Folder/Playlist: " + folder);
    this.revalidate();
    tabs.add(list);
    foldersAbsolute.add(new File(folder).getAbsolutePath());
  }

  /**
   * Runs a master revalidation of all of the
   * FileLists and checks if every added folder exists
   * and all of it's sub-files.
   * 
   * @see com.jackmeng.app.components.bottompane.FileList#revalidateFiles()
   */
  public synchronized void mastRevalidate() {
    List<Integer> needToRemove = new ArrayList<>();
    int i = 0;
    for (FileList l : tabs) {
      if (!new File(l.getFolderInfo().getAbsolutePath()).exists()
          || !new File(l.getFolderInfo().getAbsolutePath()).isDirectory()) {
        removeTabAt(i);
        needToRemove.add(i);
        foldersAbsolute.remove(l.getFolderInfo().getAbsolutePath());
      } else {
        l.revalidateFiles();
      }
    }
    for (int ix : needToRemove) {
      tabs.remove(ix);
    }
  }
}