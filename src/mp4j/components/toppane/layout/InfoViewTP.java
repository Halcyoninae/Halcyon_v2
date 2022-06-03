package mp4j.components.toppane.layout;

import javax.swing.*;

import mp4j.Manager;

import java.awt.*;

public class InfoViewTP extends JPanel {
  public InfoViewTP() {
    super();
    setPreferredSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.INFOVIEW_MAX_WIDTH, Manager.INFOVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.INFOVIEW_MIN_WIDTH, Manager.INFOVIEW_MIN_HEIGHT));
    setBorder(BorderFactory.createLineBorder(Color.WHITE));
  }
}
