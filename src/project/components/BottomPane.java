package project.components;

import javax.swing.*;

import java.awt.*;

import project.Manager;
import project.utils.TextParser;

import java.util.List;

public class BottomPane extends JTabbedPane {
  private JComponent[] tabs;

  public BottomPane(List<BPTabs> tabs) {
    super();
    setPreferredSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    this.tabs = new JComponent[tabs.size()];
    int i = 0;
    for (BPTabs t : tabs) {
      this.tabs[i] = t.getTabContent();
      addTab(t.restrainTabName() ? TextParser.strip(t.getTabName(), Manager.TAB_VIEW_MIN_TEXT_STRIP_LENGTH)
          : t.getTabName(), t.getTabIcon(), t.getTabContent(), t.getTabToolTip());
      i++;
    }
    setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
  }

  public JComponent[] getTabs() {
    return tabs;
  }
}
