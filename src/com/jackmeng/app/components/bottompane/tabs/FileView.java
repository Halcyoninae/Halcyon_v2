package com.jackmeng.app.components.bottompane.tabs;

import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.jackmeng.ProjectManager;
import com.jackmeng.app.Global;
import com.jackmeng.app.Manager;
import com.jackmeng.app.StringManager;
import com.jackmeng.app.components.bottompane.BPTabs;
import com.jackmeng.app.events.FVRightClick;
import com.jackmeng.app.utils.FileParser;
import com.jackmeng.debug.Debugger;
import com.jackmeng.global.Pair;

/**
 * Represents the default PlayList view or FileView
 * for where the user will be able to interact with the selected files.
 * 
 * It is member belonging to the BottomPane tabs and thus implements
 * the interface {@link com.jackmeng.app.components.bottompane.BPTabs} in
 * order to be a
 * qualified tab.
 * 
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.app.components.bottompane.BottomPane
 * @see com.jackmeng.app.components.bottompane.BPTabs
 */
public class FileView extends JScrollPane implements BPTabs {
  private JTree files;
  /**
   * The map's first parameter is of type String which represents the folder's
   * ABSOLUTE PATH
   * 
   * The map's second parameter is of type Pair<String, String[]> which represents
   * the folder's NAME (stripped) and its files
   * 
   * @author Jack Meng
   * @since 3.0
   */
  private transient Map<String, Pair<String, String[]>> folders = new HashMap<>();
  private DefaultMutableTreeNode root;

  /**
   * Constructs a new FileView tab,
   * there should only be one that is held
   * globally in the {@link com.jackmeng.app.Global} class.
   */
  public FileView() {
    setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));

    root = new DefaultMutableTreeNode(StringManager.JTREE_ROOT_NAME);
    files = new JTree(root);
    files.setRootVisible(true);
    files.setShowsRootHandles(true);
    files.setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    files.setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    files.setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    files.setAutoscrolls(true);
    files.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    getViewport().add(files);
    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) files.getCellRenderer();
    Icon closedIcon = new ImageIcon(Manager.FILEVIEW_ICON_FOLDER_CLOSED);
    Icon openIcon = new ImageIcon(Manager.FILEVIEW_ICON_FOLDER_OPEN);
    Icon leafIcon = new ImageIcon(Manager.FILEVIEW_ICON_FILE);
    renderer.setClosedIcon(closedIcon);
    renderer.setOpenIcon(openIcon);
    renderer.setLeafIcon(leafIcon);
    files.addMouseListener(new FVRightClick());
    files.setCellRenderer(renderer);
  }

  /**
   * Returns a random node with a message on it for a STUB node.
   * 
   * @param message the message to be displayed on the node
   * @return a random node with a message on it
   */
  public static DefaultMutableTreeNode generateGenericNode(String message) {
    return new DefaultMutableTreeNode(message);
  }

  /**
   * Returns the attributes of all stored folders.
   * 
   * The map's first parameter is of type String which represents the folder's
   * ABSOLUTE PATH
   * 
   * The map's second parameter is of type Pair<String, String[]> which represents
   * the folder's NAME (stripped) and its files
   * 
   * @return the attributes of all stored folders
   */
  public Map<String, Pair<String, String[]>> getFolders() {
    return folders;
  }

  /**
   * Returns the root node of the tree.
   * 
   * @return the root node of the tree
   */
  public DefaultMutableTreeNode getRoot() {
    return root;
  }

  /**
   * Returns the tree.
   * 
   * @return The JTree
   */
  public JTree getTree() {
    return files;
  }

  /**
   * Removes a relative foldername from the Map of folders.
   * 
   * This is used for deep searching in which only the relative
   * folder name is provided instead of the ABSOLUTE Path.
   * 
   * @param folderName the relative foldername to be removed
   */
  public void remove(String folderName) {
    for (String key : folders.keySet()) {
      if (folders.get(key).first.equals(folderName)) {
        folders.remove(key);
        break;
      }
    }
  }

  /**
   * This method will be called and will update the FileView
   * if there are any new files or folders. made:
   * 1. If a folder does not exist anymore (either name or existence) it will be
   * removed
   * 2. If a file does not exist or is under a different name it will be removed
   * and re-added to the tree under it's new alias
   * 3. If a new file is under a directory it will be inserted into the correct
   * parent node.
   */
  public void revalidateFiles() {
    Debugger.log(folders);
    for (String key : folders.keySet()) {
      if (new File(key).exists() || new File(key).isDirectory()) {
        Pair<String, String[]> flder = folders.get(key);
        for (String str : flder.second) {
          File f = new File(key + ProjectManager.FILE_SLASH + str);
        }
      } else {
        DefaultTreeModel model = (DefaultTreeModel) files.getModel();
        model.
      }
    }
  }

  /**
   * For relative searching in which the absolute path is provided.
   * 
   * @param absolutePath the absolute path to be searched
   * @return (true || false) based on the existence of the absolute folder name
   */
  public boolean containsAbsolute(String absolutePath) {
    for (String key : folders.keySet()) {
      if (key.equals(absolutePath)) {
        return true;
      }
    }
    return false;
  }

  /**
   * For relative searching in which the relative path is provided.
   * 
   * @param relativePath the relative path to be searched
   * @return The key in which the relative path is associated
   */
  public String findKey(String folderName) {
    for (String key : folders.keySet()) {
      if (folders.get(key).first.equals(folderName)) {
        return key;
      }
    }
    return null;
  }

  /**
   * For deep searching in which only the file name is provided instead
   * of the ABSOLUTE Path which is the Map's keys.
   * 
   * @param folderName This is found as Pair.first in the Map
   * @return (true || false) based on the existence of the relative folder name
   */
  public boolean contains(String folderName) {
    for (String key : folders.keySet()) {
      if (folders.get(key).first.equals(folderName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Attempts to add an empty folder to the FileView.
   * 
   * This empty folder would be represented as a leaf
   * node in the JTree.
   * 
   * @param folder the folder to be added
   */
  public void pokeFolder(String folder) {
    folders.put(folder, new Pair<>(FileParser.folderName(folder), new String[] {}));
    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(FileParser.folderName(folder));
    root.add(newNode);
    DefaultTreeModel model = (DefaultTreeModel) files.getModel();
    model.reload();
  }

  /**
   * Attempts to add a folder with actual scannable
   * files to the FileView.
   * 
   * This scannable files are determined by {@link com.jackmeng.app.Manager}
   * 
   * @param folder The folder name
   * @param files  The files from this folder
   */
  public void pokeFolder(String folder, String[] files) {
    folders.put(folder, new Pair<>(FileParser.folderName(folder), files));
    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(FileParser.folderName(folder));
    root.add(newNode);
    for (String filet : files) {
      newNode.add(new DefaultMutableTreeNode(filet));
    }
    DefaultTreeModel model = (DefaultTreeModel) this.files.getModel();
    model.reload();
  }

  @Override
  public String getTabName() {
    return Manager.FILEVIEW_DEFAULT_TAB_NAME;
  }

  @Override
  public String getTabToolTip() {
    return Manager.FILEVIEW_DEFAULT_TAB_TOOLTIP;
  }

  @Override
  public ImageIcon getTabIcon() {
    return Global.rd.getFromAsImageIcon(Manager.PLAYLIST_TAB_ICON);
  }

  @Override
  public JComponent getTabContent() {
    return this;
  }
}