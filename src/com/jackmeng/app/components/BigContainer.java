package com.jackmeng.app.components;

import javax.swing.*;

import com.jackmeng.app.events.ForceMaxSize;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

import java.awt.*;

/**
 * BigContainer is the main window for the program,
 * it is the parent container for BottomPane and TopPane.
 * 
 * It performs no other tasks but to align the components together
 * in a top-to-bottom fashion.
 * 
 * @see com.jackmeng.app.components.bottompane.BottomPane
 * @see com.jackmeng.app.components.toppane.TopPane
 * 
 * @author Jack Meng
 * @since 3.0
 */
public class BigContainer implements Runnable {
  private JFrame container;
  private JSplitPane mainPane;

  /**
   * A JSplitPane contains a BottomPane and TopPane that
   * will be used as the content pane for the JFrame.
   * @param mainPane The JSplitPane instance to attach with
   */
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

  /**
   * Returns the JFrame instance
   * @return The JFrame instance
   */
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