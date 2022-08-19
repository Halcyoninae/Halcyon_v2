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

package com.jackmeng.halcyoninae.cosmos.components.bottompane;

import javax.swing.*;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.FileList;
import com.jackmeng.halcyoninae.cosmos.inheritable.TabButton;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.filesystem.PhysicalFolder;
import com.jackmeng.halcyoninae.halcyon.runtime.Program;

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
    private final List<FileList> tabs;

    /**
     * Holds a list of folder paths in correspondence with their index
     * in the JTabbedPane.
     */
    private final Map<String, Integer> tabsMap;

    /**
     * Creates a bottom viewport
     */
    public BottomPane() {
        super();
        tabsMap = new HashMap<>();

        setPreferredSize(new Dimension(FileList.FILEVIEW_MAX_WIDTH, FileList.FILEVIEW_MIN_HEIGHT));
        setMinimumSize(new Dimension(FileList.FILEVIEW_MIN_WIDTH, FileList.FILEVIEW_MIN_HEIGHT));
        this.tabs = new ArrayList<>();
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    /**
     * @return list that represents the
     *         FileList tabs.
     */
    public List<FileList> getTabs() {
        return tabs;
    }

    /**
     * Goes through all of the avaliable
     * FileList components and gets which ever
     * has the same instance of a JTree
     *
     * @param tree The JTree to compare against
     * @return The FileList component holding the JTree component
     */
    public FileList findByTree(JTree tree) {
        for (FileList tab : tabs) {
            if (tab.getTree().equals(tree)) {
                return tab;
            }
        }
        return null;
    }

    /**
     * @param folderAbsoluteStr A folder's absolute path
     * @return (true | | false) if said folder is within the list.
     */
    public boolean containsFolder(String folderAbsoluteStr) {
        return Program.cacher.getSavedPlaylists().contains(folderAbsoluteStr);
    }

    /**
     * @return A List of String representsin the tabs with the different names.
     */
    public List<String> getStrTabs() {
        return Program.cacher.getSavedPlaylists();
    }

    /**
     * Pokes a direct File List object
     * <p>
     * This should only be used to add a static
     * viewport, which means this tab cannot be deleted
     * by the end-user
     *
     * @param plain A File List Component
     */
    public void pokeNewFileListTab(FileList plain) {
        add(plain.getFolderInfo().getName(), plain);
        tabs.add(plain);
    }

    /**
     * Adds a new File List object to the viewport of tabs.
     *
     * @param folder An absolute path to a folder.
     */
    public void pokeNewFileListTab(String folder) {
        FileList list = new FileList(new PhysicalFolder(folder));
        Program.cacher.getSavedPlaylists().add(new File(folder).getAbsolutePath());
        add(new File(folder).getName(), list);
        TabButton button = new TabButton(this);
        setTabComponentAt(getTabCount() - 1, button);
        setToolTipTextAt(getTabCount() - 1, folder);
        tabsMap.put(folder, getTabCount() - 1);
        button.setListener(new TabButton.RemoveTabListener() {
            @Override
            public void onRemoveTab() {
                Debugger.warn("Removing tab > " + folder);
                int i = tabsMap.get(folder);
                Program.cacher.getSavedPlaylists().remove(folder);
                tabsMap.remove(folder);
                tabs.remove(i);
                Program.cacher.pingSavedPlaylists();
            }
        });
        Program.cacher.pingSavedPlaylists();
        this.revalidate();
        tabs.add(list);
    }

    /**
     * Runs a master revalidation of all of the
     * FileLists and checks if every added folder exists
     * and all of it's sub-files.
     *
     * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.FileList#revalidateFiles()
     */
    public synchronized void mastRevalidate() {
        List<Integer> needToRemove = new ArrayList<>();
        int i = 0;
        for (FileList l : tabs) {
            if (!l.isVirtual) {
                if (!new File(l.getFolderInfo().getAbsolutePath()).exists()
                        || !new File(l.getFolderInfo().getAbsolutePath()).isDirectory()) {
                    removeTabAt(i);
                    needToRemove.add(i);
                    Program.cacher.getSavedPlaylists().remove(l.getFolderInfo().getAbsolutePath());
                    tabsMap.remove(l.getFolderInfo().getAbsolutePath());
                } else {
                    l.revalidateFiles();
                }
            }
        }
        for (int ix : needToRemove) {
            tabs.remove(ix);
        }
    }
}