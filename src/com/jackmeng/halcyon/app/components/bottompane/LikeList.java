package com.jackmeng.halcyon.app.components.bottompane;

import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.debug.Program;
import com.jackmeng.halcyon.utils.VirtualFolder;

import java.io.File;
import java.util.Arrays;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class LikeList extends FileList {
  private transient VirtualFolder folder;

  public LikeList() {
    super(new VirtualFolder("Liked Tracks", Program.fetchLikedTracks()),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_FOLDER_CLOSED),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_FOLDER_OPEN),
        Global.rd.getFromAsImageIcon(Manager.FILEVIEW_ICON_LIKED_FILE));
    folder = (VirtualFolder) getFolderInfo();
  }

  public void unset(String file) {
    Debugger.unsafeLog(folder);
    try {
      for (File f : getFileMap().keySet()) {
        if (f.getName().equals(file)) {
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

  public void set(String file) {
    Debugger.unsafeLog(folder);
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(new File(file).getName());
    getFileMap().put(new File(file), node);
    super.getRoot().add(node);
    folder.addFile(new File(file));
  }

  public VirtualFolder getFolder() {
    Debugger.unsafeLog(folder);
    return folder;
  }

  public boolean isLiked(String file) {
    Debugger.unsafeLog(folder);
    return Arrays.asList(folder.getFiles()).contains(new File(file));
  }
}
