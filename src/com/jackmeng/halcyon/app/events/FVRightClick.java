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

package com.jackmeng.halcyon.app.events;

import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.jackmeng.halcyon.app.components.bottompane.TabTree;
import com.jackmeng.halcyon.app.components.dialog.AudioInfoDialog;
import com.jackmeng.halcyon.app.components.dialog.ErrorWindow;
import com.jackmeng.halcyon.audio.AudioInfo;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.constant.StringManager;
import com.jackmeng.halcyon.utils.Wrapper;

/**
 * This class handles the right click event for any JTree instance.
 *
 * This right click menu currently allows the user to hide a node from view.
 *
 * @author Jack Meng
 * @since 3.0
 * @see javax.swing.JTree
 * @see javax.swing.tree.DefaultMutableTreeNode
 */
public class FVRightClick extends MouseAdapter {
    private TabTree tree;

    public FVRightClick(TabTree tree) {
        this.tree = tree;
    }

    /**
     * DEFUNCT Constructor
     */
    public FVRightClick() {

    }

    /**
     * A function that displays the right click menu.
     *
     * @param e The MouseEvent that was triggered.
     */
    private void popup(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JTree t = (JTree) e.getSource();
        TreePath path = t.getPathForLocation(x, y);
        if (path == null) {
            return;
        }

        DefaultMutableTreeNode rcNode = (DefaultMutableTreeNode) path.getLastPathComponent();

        TreePath[] s = t.getSelectionPaths();

        boolean isSelected = false;
        if (s != null) {
            for (TreePath p : s) {
                if (p.equals(path)) {
                    isSelected = true;
                }
            }
        }

        if (!isSelected) {
            t.setSelectionPath(path);
        }

        JPopupMenu popup = new JPopupMenu();
        JMenuItem refreshMenuItem = new JMenuItem("Hide Item");
        refreshMenuItem.addActionListener(ev -> {
            try {
                DefaultMutableTreeNode parent = (DefaultMutableTreeNode) rcNode.getParent();
                parent.remove(rcNode);
                DefaultTreeModel model = (DefaultTreeModel) t.getModel();
                model.reload();
                tree.remove(rcNode.toString());
            } catch (NullPointerException excec) {
                new ErrorWindow("Hiding the root node is not allowed.").run();
            }
        });

        JMenuItem audioInfoItem = new JMenuItem("Information");
        audioInfoItem.addActionListener(ev -> {
            try {
                new Thread(() -> new AudioInfoDialog(new AudioInfo(tree.getSelectedNode(rcNode))).run()).start();
            } catch (NullPointerException excec) {
                new ErrorWindow("A root node is not a valid audio stream.").run();
            }
        });

        popup.add(refreshMenuItem);
        popup.add(audioInfoItem);
        popup.show(t, x, y);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            popup(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            popup(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Wrapper.async(() -> {
                JTree pathTree = (JTree) e.getSource();
                TreePath path = pathTree.getSelectionPath();
                if (path != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

                    if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
                        Global.ifp.setAssets(
                                new File(
                                        Global.bp.findByTree((JTree) e.getSource())
                                                .getFolderInfo()
                                                .getAbsolutePath() +
                                                ProgramResourceManager.FILE_SLASH +
                                                node.toString()));
                    }
                }
            });

        }
    }

}
