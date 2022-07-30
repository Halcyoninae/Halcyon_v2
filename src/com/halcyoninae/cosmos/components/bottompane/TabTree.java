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

package com.halcyoninae.cosmos.components.bottompane;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Represents a standard tree in the bottom pane
 * viewport.
 *
 * @author Jack Meng
 * @since 3.1
 */
public interface TabTree {
  /**
   * Calls the remove function on the JTree instance.
   *
   * @param nodeName The Node to remove from the tree.
   */
  void remove(String nodeName);

  /**
   * Gets a String representation of a selected node.
   *
   * @return The selected node's name.
   */
  String getSelectedNode(DefaultMutableTreeNode node);

  /**
   * @return The Path of the absolute tree being represented
   */
  String getPath();

  /**
   * Determines if the tree holds
   * a virtual folder.
   *
   * @return (true || false)
   */
  boolean isVirtual();
}
