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

package com.jackmeng.cosmos.components.bottompane;

import com.jackmeng.cosmos.events.FVRightClick.RightClickHideItemListener;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.debug.Program;
import com.jackmeng.halcyon.utils.VirtualFolder;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A type of non-removable FileList viewable.
 *
 * This represents the liked tracks the user's
 * liked tracks.
 *
 * In order to do such, everything here is handled
 * as a "Virtual Folder" meaning that its just
 * a list of folders to keep.
 *
 * This viewport is static meaning the user
 * cannot close this.
 *
 * @author Jack Meng
 * @since 3.1
 * @see com.jackmeng.cosmos.components.bottompane.FileList
 */
public class LikeList extends FileList {
  private final transient VirtualFolder folder;
  private static final RightClickHideItemListener itemListener = new RightClickHideItemListener() {
    @Override
    public void onRemove(String content) {
      Global.ll.unset(content);
      Debugger.good("Removed: " + content + " " + Global.ll.getFolder().getAsListFiles().contains(new File(content)));
    }
  };

  /**
   * Inits the LikeList object as a child of the FileList
   * class with a Virtual Folder to represent this viewport.
   */
  public LikeList() {
    super(new VirtualFolder("Liked", Program.fetchLikedTracks()),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_FOLDER_CLOSED),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_FOLDER_OPEN),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_LIKED_FILE), "Unlike", itemListener);
    folder = (VirtualFolder) getFolderInfo();
   }

  /**
   * Removes a track from the liked list listing.
   *
   * @param file The file's absolute path to remove from.
   */
  public void unset(String file) {
    Debugger.unsafeLog(folder.removeFile(new File(file)));
    try {
      for (File f : getFileMap().keySet()) {
        if (f.getAbsolutePath().equals(file)) {
          DefaultTreeModel model = (DefaultTreeModel) getTree().getModel();
          model.removeNodeFromParent(getFileMap().get(f));
          model.reload();
          getFileMap().remove(f);
          return;
        }
      }
    } catch (IllegalArgumentException e) {
      // IGNORE
    }
  }

  /**
   * Adss a track to the liked list listing.
   *
   * @param file The file's absolute path to add.
   */
  public void set(String file) {
    if (!folder.getAsListFiles().contains(new File(file))) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(new File(file).getName());
      getFileMap().put(new File(file), node);
      super.getRoot().add(node);
      getTree().revalidate();
      folder.addFile(new File(file));
      ((DefaultTreeModel) getTree().getModel()).reload();
      Debugger.good(file + " added");
    }
  }

  @Override
  public void revalidateFiles() {
    List<File> toRemove = new ArrayList<>();
    for (File f : getFileMap().keySet()) {
      if (!f.exists() || !f.isFile()) {
        ((DefaultTreeModel) getTree().getModel()).removeNodeFromParent(getFileMap().get(f));
        toRemove.add(f);
        Debugger.warn("File not found: " + f.getName());
      }
    }

    for (File f : toRemove) {
      getFileMap().remove(f);
    }
  }

  /**
   * Returns the virtual folder representing
   * this viewport.
   *
   * @return {@link com.jackmeng.utils.VirtualFolder}
   */
  public VirtualFolder getFolder() {
    return folder;
  }

  /**
   * Checks if a given file is "liked" meaning it
   * exists in the current virtual folder.
   *
   * @param file The file's absolute path
   * @return (true || false) if the file is contained in the list or not.
   */
  public boolean isLiked(String file) {
    return Arrays.asList(folder.getFiles()).contains(new File(file));
  }

}
