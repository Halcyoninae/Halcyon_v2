/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.cosmos.components;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.TabTree;
import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.TabTree.TabTreeSortMethod;
import com.jackmeng.halcyoninae.halcyon.runtime.Program;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.StringManager;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.ProgramResourceManager;
import com.jackmeng.halcyoninae.tailwind.AudioInfo;
import com.jackmeng.halcyoninae.tailwind.AudioInfoDialog;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
                if (e.getClickCount() == 2) {
                    JTree pathTree = (JTree) e.getSource();
                    TreePath path = pathTree.getSelectionPath();
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
                            File f;
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
                }
            } else {
                if (e.getClickCount() == 2) {
                    JTree pathTree = (JTree) e.getSource();
                    TreePath path = pathTree.getSelectionPath();
                    if (path != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        if (!node.getParent().toString().equals(StringManager.JTREE_ROOT_NAME)) {
                            File f;
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
                }
            }
        }

    }

    public interface RightClickHideItemListener {
        void onRemove(String content);
    }

}
