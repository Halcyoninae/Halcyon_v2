package project.components;

import java.util.HashSet;

import javax.swing.*;
import java.awt.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import project.Manager;
import project.utils.FileParser;

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

    root = new DefaultMutableTreeNode("MP4J - PlayList Handler");
    root.add(generateGenericNode("Click [+] to add a folder!"));
    files = new JTree(root);
    files.setRootVisible(true);
    files.setShowsRootHandles(true);
    files.setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    files.setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    files.setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    getViewport().add(files);
  }

  public static DefaultMutableTreeNode generateGenericNode(String message) {
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(message);
    return node;
  }

  public void pokeFolder(String folder) {
    folders.add(folder);
    DefaultMutableTreeNode node = new DefaultMutableTreeNode(FileParser.getDirectoryName(folder));
    DefaultTreeModel parent = (DefaultTreeModel) files.getModel();
    DefaultMutableTreeNode root2 = (DefaultMutableTreeNode) parent.getRoot();
    root2.add(node);
    parent.reload(root2);
    files.updateUI();
  }
}
