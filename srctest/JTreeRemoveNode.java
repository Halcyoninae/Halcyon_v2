
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JTreeRemoveNode extends JFrame {
  public JTreeRemoveNode() throws HeadlessException {
    initializeUI();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new JTreeRemoveNode().setVisible(true));
  }

  private void initializeUI() {
    setSize(400, 400);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Address Book");
    String[] names = new String[] { "Alice", "Bob", "Carol", "Mallory" };
    for (String name : names) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);
      root.add(node);
    }

    final JTree tree = new JTree(root);
    JScrollPane pane = new JScrollPane(tree);
    pane.setPreferredSize(new Dimension(400, 400));

    JButton removeButton = new JButton("Remove");
    removeButton.addActionListener(e -> {
      DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

      TreePath[] paths = tree.getSelectionPaths();
      if (paths != null) {
        for (TreePath path : paths) {
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
          if (node.getParent() != null) {
            model.removeNodeFromParent(node);
          }
        }
      }
    });

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(tree, BorderLayout.CENTER);
    getContentPane().add(removeButton, BorderLayout.SOUTH);
  }
}