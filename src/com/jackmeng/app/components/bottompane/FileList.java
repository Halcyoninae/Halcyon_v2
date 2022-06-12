package com.jackmeng.app.components.bottompane;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

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
public class FileList extends JScrollPane {
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

  private DefaultMutableTreeNode root;

  public FileList(FolderInfo info) {
    super();
    fileMap = new HashMap<>();
    root = new DefaultMutableTreeNode(info.getName());
  }
}
