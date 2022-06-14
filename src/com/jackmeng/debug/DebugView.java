package com.jackmeng.debug;

import javax.swing.*;

import com.jackmeng.constant.Manager;

import java.awt.*;

/**
 * This class is kind of like a plugin to the main program
 * as it functions as a new tab in the {@link com.jackmeng.app.components.bottompane.BottomPane}
 * class.
 * 
 * It displays helpful debug information regarding the running program.
 * 
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.app.components.bottompane.BottomPane
 */
public class DebugView extends JScrollPane {
  private JEditorPane text;

  /**
   * Constructs the JScrollPane tab view
   */
  public DebugView() {
    super();
    setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));

    text = new JEditorPane();
    text.setEditable(false);
    text.setContentType("text/html");
    text.setText("<html><body><p>" + getMemoryUsage() + "</p></body></html>");
    getViewport().add(text);

    new Thread(() -> {
      while (true) {
        text.setText("<html><body><p>" + getMemoryUsage() + "</p></body></html>");
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

  }

  /**
   * Returns the memory usage for the first debug option
   * 
   * @return String representation of memory usage
   */
  private static String getMemoryUsage() {
    return "Memory (mB): " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
        + "/" + Runtime.getRuntime().totalMemory() / 1024 / 1024;
  }
}
