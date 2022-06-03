package mp4j.components.toppane.layout;

import javax.swing.*;

import mp4j.Manager;

import java.awt.*;

public class ButtonControlTP extends JPanel {
  public ButtonControlTP() {
    super();
    setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setBorder(BorderFactory.createLineBorder(Color.WHITE));
  }
}
