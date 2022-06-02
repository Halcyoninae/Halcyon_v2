package project.components;

import javax.swing.*;

import java.awt.*;

import project.Manager;
import project.utils.TextParser;

import java.util.Map;

public class BottomPane extends JTabbedPane {
  private JComponent[] tabs;

  public BottomPane(Map<String, JComponent> tabs) {
    super();
    setPreferredSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    this.tabs = new JComponent[tabs.size()];
    int i = 0;
    for (String s : tabs.keySet()) {
      addTab(TextParser.strip(s, Manager.TAB_VIEW_MAX_CHAR_LENGTH), tabs.get(s));
      this.tabs[i] = tabs.get(s);
      i++;
    }
  }

  public JComponent[] getTabs() {
    return tabs;
  }
}
