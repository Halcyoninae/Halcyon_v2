package com.jackmeng.app.components.bottompane;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeModel;

import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.events.FVRightClick;
import com.jackmeng.app.utils.FolderInfo;

/**
 * Represents a Pane containing a list of files for only
 * one directory. It will not contain any sub-directories.
 * 
 * This file list can contain any file type, but it will be decided
 * beforehand.
 * 
 * This mechanism suggested by FEATURES#8 and deprecated
 * the original tabs mechanism of 3.0.
 * 
 * @author Jack Meng
 * @since 3.1
 */
public class FileList extends JScrollPane implements TabTree {
  private JTree tree;

  /**
   * Represents a list of collected files throughout the
   * current selected folder for this instance of a FileList.
   * 
   * Parameter 1: {@link java.io.File} A file object representing a file in the
   * folder.
   * Parameter 2: {@link javax.swing.tree.DefaultMutableTreeNode} The node
   * instance of the file as represented on the JTree.
   */
  private Map<File, DefaultMutableTreeNode> fileMap;

  private transient FolderInfo info;

  private DefaultMutableTreeNode root;

  public FileList(FolderInfo info) {
    super();
    this.info = info;
    fileMap = new HashMap<>();
    root = new DefaultMutableTreeNode(info.getName());

    setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));

    for (File f : info.getFiles(Manager.ALLOWED_FORMATS)) {
      if (f != null) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(f.getName());
        fileMap.put(f, node);
        root.add(node);
      }
    }

    tree = new JTree(root);
    tree.setRootVisible(true);
    tree.setShowsRootHandles(true);
    tree.setExpandsSelectedPaths(true);
    tree.setEditable(false);
    tree.setRequestFocusEnabled(false);
    tree.setScrollsOnExpand(true);
    tree.setAutoscrolls(true);
    tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
    Icon closedIcon = new ImageIcon(Manager.FILEVIEW_ICON_FOLDER_CLOSED);
    Icon openIcon = new ImageIcon(Manager.FILEVIEW_ICON_FOLDER_OPEN);
    Icon leafIcon = new ImageIcon(Manager.FILEVIEW_ICON_FILE);
    renderer.setClosedIcon(closedIcon);
    renderer.setOpenIcon(openIcon);
    renderer.setLeafIcon(leafIcon);

    tree.addMouseListener(new FVRightClick(this));
    tree.setCellRenderer(renderer);

    tree.addTreeSelectionListener(Global.ifp);

    getViewport().add(tree);
  }

  public JTree getTree() {
    return tree;
  }

  public FolderInfo getFolderInfo() {
    return info;
  }

  /**
   * This function facilitates reloading the current
   * folder:
   * 
   * 1. If a file doesn't exist anymore, it will be removed
   * 2. If a new file has been added, it will be added into the Tree
   * 
   * The detection on if a folder exists or not is up to the parent
   * BottomPane {@link com.jackmeng.app.components.bottompane.BottomPane}.
   */
  public void revalidateFiles() {
    for (File f : info.getFiles(Manager.ALLOWED_FORMATS)) {
      if (f != null) {
        if (!fileMap.containsKey(f)) {
          DefaultMutableTreeNode node = new DefaultMutableTreeNode(f.getName());
          node.setParent(root);
          fileMap.put(f, node);
          root.add(node);
        }
      }
    }
    for (File f : fileMap.keySet()) {
      if (!f.exists() || !f.isFile()) {
        ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(fileMap.get(f));
        fileMap.remove(f);
      }
    }
  }

  @Override
  public void remove(String nodeName) {
    try {
      for (File f : fileMap.keySet()) {
        if (f.getName().equals(nodeName)) {
          DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
          model.removeNodeFromParent(fileMap.get(f));
          model.reload();
          fileMap.remove(f);
        }
      }
    } catch (IllegalArgumentException e) {
      // IGNORE
    }
  }

  @Override
  public String getSelectedNode(DefaultMutableTreeNode node) {
    for(File f : fileMap.keySet()) {
      if(fileMap.get(f).equals(node)) {
        return f.getAbsolutePath();
      }
    }
    return "";
  }
}
