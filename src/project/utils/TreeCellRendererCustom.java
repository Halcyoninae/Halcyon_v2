package project.utils;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.Icon;

public class TreeCellRendererCustom extends DefaultTreeCellRenderer {
  public TreeCellRendererCustom(Icon opened, Icon closed, Icon leaf) {
    super();
    setOpenIcon(opened);
    setClosedIcon(closed);
    setLeafIcon(leaf);
  }

  @Override
  public void setText(String text) {
    if (text.equals("+")) {
      text = "";
    }
    super.setText(text);
  }
}
