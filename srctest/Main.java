import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/*ww  w . ja v  a2s  . c  om*/
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class Main extends JFrame {
    public Main() {
        JTree myTree = new JTree();
        myTree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        myTree.addMouseListener(new MyMouseAdapter());

        add(new JScrollPane(myTree));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}

class MyMouseAdapter extends MouseAdapter {
    private void myPopupEvent(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JTree tree = (JTree) e.getSource();
        TreePath path = tree.getPathForLocation(x, y);
        if (path == null)
            return;

        DefaultMutableTreeNode rightClickedNode = (DefaultMutableTreeNode) path
                .getLastPathComponent();

        TreePath[] selectionPaths = tree.getSelectionPaths();

        boolean isSelected = false;
        if (selectionPaths != null) {
            for (TreePath selectionPath : selectionPaths) {
                if (selectionPath.equals(path)) {
                    isSelected = true;
                }
            }
        }
        if (!isSelected) {
            tree.setSelectionPath(path);
        }
        if (rightClickedNode.isLeaf()) {
            JPopupMenu popup = new JPopupMenu();
            final JMenuItem refreshMenuItem = new JMenuItem("refresh");
            refreshMenuItem.addActionListener(ev->System.out.println("refresh!"));
            popup.add(refreshMenuItem);
            popup.show(tree, x, y);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            myPopupEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            myPopupEvent(e);
    }
}