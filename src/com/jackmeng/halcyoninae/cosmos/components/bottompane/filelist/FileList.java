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

package com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist;

import com.jackmeng.halcyoninae.cosmos.events.FVRightClick;
import com.jackmeng.halcyoninae.cosmos.events.FVRightClick.RightClickHideItemListener;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.filesystem.PhysicalFolder;
import com.jackmeng.halcyoninae.halcyon.filesystem.VirtualFolder;
import com.jackmeng.halcyoninae.halcyon.runtime.Program;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;

/**
 * Represents a Pane containing a list of files for only
 * one directory. It will not contain any sub-directories.
 * <p>
 * This file list can contain any file type, but it will be decided
 * beforehand.
 * <p>
 * This mechanism suggested by FEATURES#8 and deprecated
 * the original tabs mechanism of 3.0.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class FileList extends JScrollPane implements TabTree {
    /// FileView Config START
    public static final String FILEVIEW_ICON_FOLDER_OPEN    = Manager.RSC_FOLDER_NAME + "/fileview/folder_icon.png";
    public static final String FILEVIEW_ICON_FOLDER_CLOSED  = Manager.RSC_FOLDER_NAME + "/fileview/folder_icon.png";
    public static final String FILEVIEW_ICON_FILE           = Manager.RSC_FOLDER_NAME + "/fileview/leaf.png";
    public static final String FILEVIEW_DEFAULT_FOLDER_ICON = Manager.RSC_FOLDER_NAME + "/fileview/folder_icon.png";
    public static final String FILEVIEW_ICON_LIKED_FILE     = Manager.RSC_FOLDER_NAME + "/fileview/leaf_like.png";
    public static final int FILEVIEW_MIN_WIDTH              = Manager.MIN_WIDTH - 70;
    public static final int FILEVIEW_MIN_HEIGHT             = Manager.MIN_HEIGHT - 50 / 2;
    public static final int FILEVIEW_MAX_WIDTH              = Manager.MAX_WIDTH - 50;
    public static final int FILEVIEW_MAX_HEIGHT             = Manager.MAX_HEIGHT + 50 / 2 - 40;
    private final JTree tree;
    /**
     * Represents a list of collected files throughout the
     * current selected folder for this instance of a FileList.
     * <p>
     * Parameter 1: {@link java.io.File} A file object representing a file in the
     * folder.
     * Parameter 2: {@link javax.swing.tree.DefaultMutableTreeNode} The node
     * instance of the file as represented on the JTree.
     */
    private final transient Map<File, DefaultMutableTreeNode> fileMap;
    private final transient PhysicalFolder info;
    private final DefaultMutableTreeNode root;
    public boolean isVirtual;
    /// FileView Config END

    public FileList(PhysicalFolder info, Icon closed, Icon open, Icon leaf, String rightClickHideString,
                    RightClickHideItemListener hideStringTask) {
        super();
        this.info = info;
        fileMap = new HashMap<>();
        root = new DefaultMutableTreeNode(info.getName());
        isVirtual = info instanceof VirtualFolder;
        setAutoscrolls(true);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        setPreferredSize(new Dimension(FILEVIEW_MIN_WIDTH, FILEVIEW_MIN_HEIGHT));
        getVerticalScrollBar().setForeground(ColorManager.MAIN_FG_THEME);
        getHorizontalScrollBar().setForeground(ColorManager.MAIN_FG_THEME);
        setMinimumSize(new Dimension(FILEVIEW_MIN_WIDTH, FILEVIEW_MIN_HEIGHT));
        setBorder(null);
        Debugger.warn(info);
        for (File f : info.getFiles(Manager.ALLOWED_FORMATS)) {
            if (f != null) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(isVirtual ? f.getAbsolutePath() : f.getName());
                fileMap.put(f, node);
                root.add(node);
                node.setParent(root);
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
        renderer.setClosedIcon(DeImage.resizeImage((ImageIcon) closed, 16, 16));
        renderer.setOpenIcon(DeImage.resizeImage((ImageIcon) open, 16, 16));
        renderer.setLeafIcon(DeImage.resizeImage((ImageIcon) leaf, 16, 16));

        tree.addMouseListener(new FVRightClick(this, rightClickHideString, hideStringTask));
        tree.setCellRenderer(renderer);

        getViewport().add(tree);
    }

    public FileList(PhysicalFolder info) {
        this.info = info;
        fileMap = new HashMap<>();
        root = new DefaultMutableTreeNode(info.getName());
        isVirtual = info instanceof VirtualFolder;
        setAutoscrolls(true);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        setPreferredSize(new Dimension(FILEVIEW_MIN_WIDTH, FILEVIEW_MIN_HEIGHT));
        setMinimumSize(new Dimension(FILEVIEW_MIN_WIDTH, FILEVIEW_MIN_HEIGHT));
        getVerticalScrollBar().setForeground(ColorManager.MAIN_FG_THEME);
        getHorizontalScrollBar().setForeground(ColorManager.MAIN_FG_THEME);
        for (File f : info.getFiles(Manager.ALLOWED_FORMATS)) {
            if (f != null && !Program.cacher.isExcluded(f.getAbsolutePath())) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(f.getName());
                fileMap.put(f, node);
                root.add(node);
                node.setParent(root);
            }
        }

        tree = new JTree(root);
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        tree.setExpandsSelectedPaths(true);
        tree.setScrollsOnExpand(true);
        tree.setEditable(false);
        tree.setRequestFocusEnabled(false);
        tree.setScrollsOnExpand(true);
        tree.setAutoscrolls(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setBorder(null);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        ImageIcon closedIcon = Global.ico.getFromAsImageIcon(FILEVIEW_ICON_FOLDER_CLOSED);
        ImageIcon openIcon = Global.ico.getFromAsImageIcon(FILEVIEW_ICON_FOLDER_OPEN);
        ImageIcon leafIcon = Global.ico.getFromAsImageIcon(FILEVIEW_ICON_FILE);
        renderer.setClosedIcon(DeImage.resizeImage(closedIcon, 16, 16));
        renderer.setOpenIcon(DeImage.resizeImage(openIcon, 16, 16));
        renderer.setLeafIcon(DeImage.resizeImage(leafIcon, 16, 16));

        tree.addMouseListener(new FVRightClick(this));
        tree.setCellRenderer(renderer);

        getViewport().add(tree);
    }

    /**
     * @return The JTree representing this viewport.
     */
    public JTree getTree() {
        return tree;
    }

    /**
     * @return A FolderInfo object representing this FileList
     */
    public PhysicalFolder getFolderInfo() {
        return info;
    }

    /**
     * @return A Node that represents the root node.
     */
    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    /**
     * @return Returns the default file map with each File object having a node.
     */
    public Map<File, DefaultMutableTreeNode> getFileMap() {
        return fileMap;
    }

    /**
     * This function facilitates reloading the current
     * folder:
     * <p>
     * 1. If a file doesn't exist anymore, it will be removed
     * 2. If a new file has been added, it will be added into the Tree
     * <p>
     * The detection on if a folder exists or not is up to the parent
     * BottomPane {@link com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane}.
     */
    public void revalidateFiles() {
        for (File f : info.getFiles(Manager.ALLOWED_FORMATS)) {
            if (f != null && !fileMap.containsKey(f) && !Program.cacher.isExcluded(f.getAbsolutePath())) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(f.getName());
                fileMap.put(f, node);
                root.add(node);
                ((DefaultTreeModel) tree.getModel()).reload();
            }
        }
        List<File> toRemove = new ArrayList<>(20);
        for (File f : fileMap.keySet()) {
            if (!f.exists() || !f.isFile()
                || Program.cacher.isExcluded(f.getAbsolutePath()) && fileMap.get(f).getParent() != null) {
                ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(fileMap.get(f));
                toRemove.add(f);
            }
        }
        for (File f : toRemove) {
            fileMap.remove(f);
        }
    }

    /**
     * @param nodeName
     */
    @Override
    public void remove(String nodeName) {
        try {
            Debugger.warn(nodeName);
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

    /**
     * @param node
     * @return String
     */
    @Override
    public String getSelectedNode(DefaultMutableTreeNode node) {
        for (File f : fileMap.keySet()) {
            if (fileMap.get(f).equals(node)) {
                return f.getAbsolutePath();
            }
        }
        return "";
    }

    /**
     * @return String
     */
    @Override
    public String getPath() {
        return info.getAbsolutePath();
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isVirtual() {
        return isVirtual;
    }


    /**
     * @param e
     */
    @Override
    public void sort(TabTreeSortMethod e) {
        synchronized (fileMap) {
            if (e.equals(TabTreeSortMethod.ALPHABETICAL)) {
                List<DefaultMutableTreeNode> nodes = new ArrayList<>();
                for (File f : fileMap.keySet()) {
                    nodes.add(fileMap.get(f));
                }
                nodes.sort(Comparator.comparing(o -> ((String) o.getUserObject())));
                for (DefaultMutableTreeNode node : nodes) {
                    root.remove(node);
                    root.add(node);
                }
                ((DefaultTreeModel) tree.getModel()).reload();
            } else if (e.equals(TabTreeSortMethod.REV_ALPHABETICAL)) {
                List<DefaultMutableTreeNode> nodes = new ArrayList<>();
                for (File f : fileMap.keySet()) {
                    nodes.add(fileMap.get(f));
                }
                nodes.sort((o1, o2) -> ((String) o2.getUserObject()).compareTo(((String) o1.getUserObject())));
                for (DefaultMutableTreeNode node : nodes) {
                    root.remove(node);
                    root.add(node);
                }
                ((DefaultTreeModel) tree.getModel()).reload();
            } else if (e.equals(TabTreeSortMethod.SHUFFLE)) {
                List<DefaultMutableTreeNode> nodes = new ArrayList<>();
                for (File f : fileMap.keySet()) {
                    nodes.add(fileMap.get(f));
                }
                Collections.shuffle(nodes);
                for (DefaultMutableTreeNode node : nodes) {
                    root.remove(node);
                    root.add(node);
                }
                ((DefaultTreeModel) tree.getModel()).reload();
            }
        }
    }


    /**
     * @return FileList
     */
    @Override
    public FileList getFileList() {
        return this;
    }
}
