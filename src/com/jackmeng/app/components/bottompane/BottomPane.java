package com.jackmeng.app.components.bottompane;

import javax.swing.*;

import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.utils.FolderInfo;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.awt.event.*;

/**
 * Handles the Tabs in the BottomPane.
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class BottomPane extends JTabbedPane {
  private ArrayList<FileList> tabs;
  private int indexCurr = 0;
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

  public List<String> getStrTabs() {
    return foldersAbsolute;
  }

  public void pokeNewFileListTab(String folder) {
    FileList list = new FileList(new FolderInfo(folder));
    addTab(new File(folder).getName(), Global.rd.getFromAsImageIcon(Manager.FILEVIEW_DEFAULT_FOLDER_ICON), list, "Folder/Playlist: " + folder);
    this.revalidate();
    tabs.add(list);
  }
}