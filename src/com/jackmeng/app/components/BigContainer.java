package com.jackmeng.app.components;

import javax.swing.*;

import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.events.ForceMaxSize;

import java.awt.*;

public class BigContainer implements Runnable {
  private JFrame container;
  private JSplitPane mainPane;

  public BigContainer(JSplitPane mainPane) {
    this.mainPane = mainPane;
    this.mainPane.setBorder(BorderFactory.createEmptyBorder());
    container = new JFrame("Halcyon Beta ~ exoad");
    container.setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    container.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT));
    container.setMaximumSize(new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT));
    container.addComponentListener(
        new ForceMaxSize(container, Manager.MAX_WIDTH, Manager.MAX_HEIGHT, Manager.MIN_WIDTH, Manager.MIN_HEIGHT));
    container.getContentPane().add(mainPane);
  }

  public JFrame getFrame() {
    return container;
  }

  @Override
  public void run() {
    container.pack();
    container.setLocationRelativeTo(null);
    container.setVisible(true);
  }

}