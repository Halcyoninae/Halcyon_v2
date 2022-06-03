package project.components.tabs;

import java.util.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import global.Pair;
import project.Global;
import project.Manager;
import project.components.BPTabs;
import project.events.FVRightClick;
import project.utils.FileParser;

public class FileView extends JScrollPane implements BPTabs {
  private JTree files;
  private transient Map<String, Pair<String, String[]>> folders = new HashMap<>();
  private DefaultMutableTreeNode root;

  public FileView() {
    setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));

    root = new DefaultMutableTreeNode("Your Playlist(s)");
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
    files.addMouseListener(new FVRightClick(files));
    files.setCellRenderer(renderer);
  }

  public static DefaultMutableTreeNode generateGenericNode(String message) {
    return new DefaultMutableTreeNode(message);
  }

  public Map<String, Pair<String, String[]>> getFolders() {
    return folders;
  }

  public void pokeFolder(String folder) {
    folders.put(folder, new Pair<>(FileParser.folderName(folder), new String[] {}));
    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(FileParser.folderName(folder));
    root.add(newNode);
    DefaultTreeModel model = (DefaultTreeModel) files.getModel();
    model.reload();
  }

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