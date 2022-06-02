package project.components.toppane.layout;

import javax.swing.*;

import project.Manager;

import java.awt.*;

public class InfoViewTP extends JPanel {
  public InfoViewTP() {
    super();
    setPreferredSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_WIDTH));
    setMaximumSize(new Dimension(Manager.INFOVIEW_MAX_WIDTH, Manager.INFOVIEW_MAX_WIDTH));
    setMinimumSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_WIDTH));

    setOpaque(true);
    setBackground(Color.RED);
  }
}
