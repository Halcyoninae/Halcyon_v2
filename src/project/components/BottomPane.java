package project.components;

import javax.swing.*;

import com.formdev.flatlaf.intellijthemes.FlatVuesionIJTheme;

import mdlaf.utils.MaterialBorders;

import java.awt.*;

import project.Global;
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
      if (s.contains("Attributes")) {
        addTab(TextParser.strip(s, Manager.TAB_VIEW_MAX_CHAR_LENGTH),
            Global.rd.getFromAsImageIcon(Manager.ATTRIBUTES_TAB_ICON), tabs.get(s));
      } else if (s.contains("Playlist")) {
        addTab(TextParser.strip(s, Manager.TAB_VIEW_MAX_CHAR_LENGTH),
            Global.rd.getFromAsImageIcon(Manager.PLAYLIST_TAB_ICON), tabs.get(s));
      } else {
        addTab(TextParser.strip(s, Manager.TAB_VIEW_MAX_CHAR_LENGTH), tabs.get(s));
      }
      this.tabs[i] = tabs.get(s);
      i++;
    }
    setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
  }

  public JComponent[] getTabs() {
    return tabs;
  }
}
