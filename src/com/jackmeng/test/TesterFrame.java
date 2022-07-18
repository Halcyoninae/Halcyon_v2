package com.jackmeng.test;

import javax.swing.*;
import java.awt.*;

public class TesterFrame extends JFrame implements Runnable {
  public TesterFrame(JPanel panel) {
    super("Testing");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setPreferredSize(panel.getPreferredSize());
    getContentPane().add(panel);
  }

  @Override
  public void run() {
    pack();
    setVisible(true);
  }
}
