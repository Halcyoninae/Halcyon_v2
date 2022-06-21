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

import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.debug.Program;
import com.jackmeng.halcyon.utils.VirtualFolder;

import java.io.File;
import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

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
 * @see com.jackmeng.halcyon.app.components.bottompane.FileList
 */
public class LikeList extends FileList {
  private transient VirtualFolder folder;

  /**
   * Inits the LikeList object as a child of the FileList
   * class with a Virtual Folder to represent this viewport.
   */
  public LikeList() {
    super(new VirtualFolder("Liked Tracks", Program.fetchLikedTracks()),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_FOLDER_CLOSED),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_FOLDER_OPEN),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_LIKED_FILE));
    folder = (VirtualFolder) getFolderInfo();
  }

  /**
   * Removes a track from the liked list listing.
   *
   * @param file The file's absolute path to remove from.
   */
  public void unset(String file) {
    try {
      for (File f : getFileMap().keySet()) {
        if (f.getAbsolutePath().equals(file)) {
          DefaultTreeModel model = (DefaultTreeModel) getTree().getModel();
          model.removeNodeFromParent(getFileMap().get(f));
          model.reload();
          getFileMap().remove(f);
          folder.removeFile(new File(file));
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
      folder.addFile(new File(file));
    }
  }

  /**
   * Returns the virtual folder representing
   * this viewport.
   *
   * @return {@link com.jackmeng.utils.VirtualFolder.}
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
