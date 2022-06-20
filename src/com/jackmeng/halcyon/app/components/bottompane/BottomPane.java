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

package com.jackmeng.halcyon.app.components.bottompane;

import javax.swing.*;

import com.jackmeng.halcyon.app.components.inheritable.TabButton;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.FolderInfo;
import com.jackmeng.halcyon.utils.Wrapper;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  /**
   * Holds a list of folder paths in correspondence with their index
   * in the JTabbedPane.
   */
  private Map<String, Integer> tabsMap;

  public BottomPane() {
    super();
    foldersAbsolute = new ArrayList<>();
    tabsMap = new HashMap<>();

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
    foldersAbsolute.add(new File(folder).getAbsolutePath());
    add(new File(folder).getName(), list);
    TabButton button = new TabButton(this);
    setTabComponentAt(getTabCount() - 1, button);
    tabsMap.put(folder, getTabCount() - 1);
    button.setListener(new TabButton.RemoveTabListener() {
      @Override
      public void onRemoveTab() {
        Wrapper.safeLog(() -> {
          int i = tabsMap.get(folder);
          foldersAbsolute.remove(folder);
          tabsMap.remove(folder);
          tabs.remove(i);
        }, false);
      }
    });
    this.revalidate();
    tabs.add(list);
  }

  /**
   * Runs a master revalidation of all of the
   * FileLists and checks if every added folder exists
   * and all of it's sub-files.
   *
   * @see com.jackmeng.halcyon.app.components.bottompane.FileList#revalidateFiles()
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
        tabsMap.remove(l.getFolderInfo().getAbsolutePath());
      } else {
        l.revalidateFiles();
      }
    }
    for (int ix : needToRemove) {
      tabs.remove(ix);
    }
  }
}