package com.jackmeng.app.components.bottompane;

import javax.swing.tree.DefaultMutableTreeNode;

import com.jackmeng.audio.AudioInfo;

public interface TabTree {
  /**
   * Calls the remove function on the JTree instance.
   * @param nodeName The Node to remove from the tree.
   */
  void remove(String nodeName);

  /**
   * Gets a String representation of a selected node.
   * 
   * @return The selected node's name.
   */
  String getSelectedNode(DefaultMutableTreeNode node);
}
