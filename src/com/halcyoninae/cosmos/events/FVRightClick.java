/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.halcyoninae.cosmos.events;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.halcyoninae.cosmos.components.bottompane.filelist.TabTree;
import com.halcyoninae.cosmos.components.bottompane.filelist.TabTree.TabTreeSortMethod;
import com.halcyoninae.cosmos.dialog.ErrorWindow;
import com.halcyoninae.cosmos.dialog.StraightTextDialog;
import com.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.StringManager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.runtime.Program;
import com.halcyoninae.tailwind.audioinfo.AudioInfo;
import com.halcyoninae.tailwind.audioinfo.AudioInfoDialog;

/**
 * This class handles the right click event for any JTree instance.
 * <p>
 * This right click menu currently allows the user to hide a node from view.
 *
 * @author Jack Meng
 * @see javax.swing.JTree
 * @see javax.swing.tree.DefaultMutableTreeNode
 * @since 3.0
 */
public class FVRightClick extends MouseAdapter {

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
        // uwu defunct!
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
                    Debugger.info("Removing Physical: " + tree.getSelectedNode(rcNode));
                    hideTask.onRemove(tree.getSelectedNode(rcNode));
                    if (hideTask == null)
                        Program.cacher.pingExcludedTracks(tree.getPath() + "/" + rcNode);
                } catch (NullPointerException excec) {
                    // IGNORED

                }

            });
        }
        JMenuItem sortAZ = null;
        JMenuItem sortZA = null;
        JMenuItem sortShuffle = null;
        if (rcNode.equals(t.getModel().getRoot())) {
            sortAZ = new JMenuItem("Sort A-Z");
            sortAZ.addActionListener(ev -> tree.sort(TabTreeSortMethod.ALPHABETICAL));
            sortZA = new JMenuItem("Sort Z-A");
            sortZA.addActionListener(ev -> tree.sort(TabTreeSortMethod.REV_ALPHABETICAL));
            sortShuffle = new JMenuItem("Sort Shuffle");
            sortShuffle.addActionListener(ev -> tree.sort(TabTreeSortMethod.SHUFFLE));
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
        if (sortAZ != null) {
            popup.add(sortAZ);
            popup.add(sortZA);
            popup.add(sortShuffle);
        }
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
                    JTree pathTree = (JTree) e.getSource();
                    TreePath path = pathTree.getSelectionPath();
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
                            File f = null;
                            if (tree.isVirtual()) {
                                f = new File(node.toString());
                            } else {
                                f = new File(
                                        Global.bp.findByTree((JTree) e.getSource())
                                                .getFolderInfo()
                                                .getAbsolutePath() +
                                                ProgramResourceManager.FILE_SLASH +
                                                node);
                            }
                            File f2 = f;
                            Debugger.info(f2);

                            SwingUtilities.invokeLater(() -> Global.ifp.setAssets(f2));
                        }
                    }
                    lastJTree = pathTree;
                    rightClicks = 0;
                } else if (rightClicks == 0 || rightClicks == 1) {
                    rightClicks++;
                }
            } else {
                JTree pathTree = (JTree) e.getSource();
                TreePath path = pathTree.getSelectionPath();
                if (path != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
                        File f = null;
                        if (tree.isVirtual()) {
                            f = new File(node.toString());
                        } else {
                            f = new File(
                                    Global.bp.findByTree((JTree) e.getSource())
                                            .getFolderInfo()
                                            .getAbsolutePath() +
                                            ProgramResourceManager.FILE_SLASH +
                                            node);
                        }
                        File f2 = f;
                        Debugger.info(f2);
                        SwingUtilities.invokeLater(() -> Global.ifp.setAssets(f2));
                    }

                }
                lastJTree = pathTree;
                rightClicks = 0;
            }
        }

    }

    public interface RightClickHideItemListener {
        void onRemove(String content);
    }

}
