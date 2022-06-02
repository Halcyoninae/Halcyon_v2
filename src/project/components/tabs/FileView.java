package project.components.tabs;

import java.util.HashSet;

import javax.swing.*;
import java.awt.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import project.Manager;

public class FileView extends JScrollPane {
  private JTree files;
  private HashSet<String> folders = new HashSet<>();
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
    getViewport().add(files);
  }

  public static DefaultMutableTreeNode generateGenericNode(String message) {
    return new DefaultMutableTreeNode(message);
  }

  public void pokeFolder(String folder) {
    folders.add(folder);
    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(folder);
    root.add(newNode);
    DefaultTreeModel model = (DefaultTreeModel) files.getModel();
    model.reload();
  }

  public void pokeFolder(String folder, String[] files) {
    folders.add(folder);
    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(folder);
    root.add(newNode);
    for (String filet : files) {
      newNode.add(new DefaultMutableTreeNode(filet));
    }
    DefaultTreeModel model = (DefaultTreeModel) this.files.getModel();
    model.reload();
  }
}
