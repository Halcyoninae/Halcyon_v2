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

package com.jackmeng.cosmos.events;

import com.jackmeng.cosmos.components.bottompane.TabTree;
import com.jackmeng.cosmos.components.dialog.AudioInfoDialog;
import com.jackmeng.cosmos.components.dialog.ErrorWindow;
import com.jackmeng.cosmos.components.dialog.StraightTextDialog;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.ProgramResourceManager;
import com.jackmeng.halcyon.constant.StringManager;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.Wrapper;
import com.jackmeng.tailwind.AudioInfo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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

    public interface RightClickHideItemListener {
        void onRemove(String content);
    }

    private TabTree tree;
    private JTree lastJTree;
    private int rightClicks = 0;
    private String hideString = "Hide Item";

    private RightClickHideItemListener hideTask;

    public FVRightClick(TabTree tree, String hideItemString, RightClickHideItemListener hideTask) {
        this(tree);
        this.hideTask = hideTask;
        this.hideString = hideItemString;
    }

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
                    break;
                }
            }
        }

        if (!isSelected) {
            t.setSelectionPath(path);
        }

        JPopupMenu popup = new JPopupMenu();
        JMenuItem refreshMenuItem = null;
        if (!rcNode.equals(t.getModel().getRoot())) {
            refreshMenuItem = new JMenuItem(hideString);
            refreshMenuItem.addActionListener(ev -> {
                try {
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) rcNode.getParent();
                    parent.remove(rcNode);
                    DefaultTreeModel model = (DefaultTreeModel) t.getModel();
                    model.reload();
                    tree.remove(rcNode.toString());
                    if (hideTask != null) {
                        Debugger.unsafeLog("Dispatching hide_task callable...");
                        hideTask.onRemove(tree.getSelectedNode(rcNode));
                    }
                } catch (NullPointerException excec) {
                    // IGNORED
                }
            });
        }

        JMenuItem audioInfoItem = new JMenuItem("Information");
        audioInfoItem.addActionListener(ev -> {
            try {
                if (!rcNode.equals(t.getModel().getRoot())) {
                    new Thread(() -> new AudioInfoDialog(new AudioInfo(tree.getSelectedNode(rcNode))).run()).start();
                } else {
                    new StraightTextDialog(
                            "<html><body><p><strong>Folder:</strong> " + tree.getPath() + "</p></body></html>").run();
                }
            } catch (NullPointerException excec) {
                new ErrorWindow("A root node is not a valid audio stream.").run();
            }
        });
        if (refreshMenuItem != null)
            popup.add(refreshMenuItem);
        popup.add(audioInfoItem);
        popup.show(t, x, y);

    }

    /**
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            popup(e);
        }
    }

    /**
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            popup(e);
        }
    }

    /**
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (lastJTree == null) {
                lastJTree = (JTree) e.getSource();
            }
            if (!lastJTree.equals(e.getSource())) {
                if (rightClicks == 2) {
                    Wrapper.async(() -> {
                        JTree pathTree = (JTree) e.getSource();
                        TreePath path = pathTree.getSelectionPath();
                        if (path != null) {
                            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                            if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
                                SwingUtilities.invokeLater(() -> Global.ifp.setAssets(
                                        new File(
                                                Global.bp.findByTree((JTree) e.getSource())
                                                        .getFolderInfo()
                                                        .getAbsolutePath() +
                                                        ProgramResourceManager.FILE_SLASH +
                                                        node)));

                            }
                        }
                        lastJTree = pathTree;
                    });
                    rightClicks = 0;
                } else if (rightClicks == 0 || rightClicks == 1) {
                    rightClicks++;
                }
            } else {
                Wrapper.async(() -> {
                    JTree pathTree = (JTree) e.getSource();
                    TreePath path = pathTree.getSelectionPath();
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
                            SwingUtilities.invokeLater(() -> Global.ifp.setAssets(
                                    new File(
                                            Global.bp.findByTree((JTree) e.getSource())
                                                    .getFolderInfo()
                                                    .getAbsolutePath() +
                                                    ProgramResourceManager.FILE_SLASH +
                                                    node)));
                        }
                    }
                    lastJTree = pathTree;
                });
                rightClicks = 0;
            }
        }

    }

}
