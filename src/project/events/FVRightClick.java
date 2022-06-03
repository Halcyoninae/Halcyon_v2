package project.events;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class FVRightClick extends MouseAdapter {
    private void popup(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JTree t = (JTree) e.getSource();
        TreePath path = t.getPathForLocation(x, y);
        if(path == null) {
            return;
        }

        DefaultMutableTreeNode rcNode = (DefaultMutableTreeNode) path.getLastPathComponent();

        TreePath[] s = t.getSelectionPaths();

        boolean isSelected = false;
        if(s != null) {

        }
    }
}
