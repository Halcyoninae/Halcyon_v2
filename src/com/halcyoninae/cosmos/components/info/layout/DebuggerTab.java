package com.halcyoninae.cosmos.components.info.layout;

import com.halcyoninae.cosmos.components.info.InformationTab;
import com.halcyoninae.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a tab for the InformationDialog that shows
 * a bunch of property and information regarding the internals
 * of the system and program.
 *
 * @author Jack Meng
 * @since 3.2
 */
public class DebuggerTab extends JScrollPane implements InformationTab {
  private JEditorPane info;

  public DebuggerTab() {
    setPreferredSize(new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));
    setFocusable(false);

    info = new JEditorPane();
    info.setEditable(false);
    info.setContentType("text/html");
    info.setCaretPosition(0);

    getViewport().add(info);
    SwingUtilities.invokeLater(this::updateText);

    new Thread(() -> {
      while(true) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        SwingUtilities.invokeLater(this::updateText);
      }
    }).start();
  }

  private void updateText() {
    info.setText("<html><body><p>" + getProperties() + "<html><body><p>");
  }

  /**
   * @return String
   */
  private String getProperties() {
    StringBuilder sb = new StringBuilder();
    sb.append("memory (used/total) mB: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "/" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "<br>");
    return sb.toString();
  }

  /**
   * @return String
   */
  @Override
  public String getName() {
    return "System";
  }

  /**
   * @return String
   */
  @Override
  public String getToolTip() {
    return "View the machine's information";
  }

  /**
   * @return JComponent
   */
  @Override
  public JComponent getComponent() {
    return this;
  }

}
