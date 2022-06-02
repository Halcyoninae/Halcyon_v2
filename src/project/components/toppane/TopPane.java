package project.components.toppane;

import javax.swing.*;

import java.awt.*;

import project.Manager;
import project.components.toppane.layout.ButtonControlTP;
import project.components.toppane.layout.InfoViewTP;

public class TopPane extends JPanel {
  public TopPane(InfoViewTP ifp, ButtonControlTP bctp) {
    setPreferredSize(new Dimension(Manager.TOPPANE_MIN_WIDTH, Manager.TOPPANE_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.TOPPANE_MAX_WIDTH, Manager.TOPPANE_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.TOPPANE_MIN_WIDTH, Manager.TOPPANE_MIN_HEIGHT));

    setLayout(new BorderLayout());

    add(ifp, BorderLayout.NORTH);
    add(bctp, BorderLayout.SOUTH);
  }
}
