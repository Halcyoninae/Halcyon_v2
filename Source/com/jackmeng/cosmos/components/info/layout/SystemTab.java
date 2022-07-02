package com.jackmeng.cosmos.components.info.layout;

import javax.swing.JComponent;

import com.jackmeng.cosmos.components.info.InformationTab;
import com.jackmeng.halcyon.constant.Manager;

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
public class SystemTab extends JScrollPane implements InformationTab {
  private JEditorPane info;

  public SystemTab() {
    setPreferredSize(new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));
    setFocusable(false);

    info = new JEditorPane();
    info.setEditable(false);
    info.setContentType("text/html");
    info.setCaretPosition(0);

    getViewport().add(info);
    SwingUtilities.invokeLater(this::updateText);
  }

  private void updateText() {
    info.setText("<html><body><p>" + getProperties() + "<html><body><p>");
  }


  /**
   * @return String
   */
  private String getProperties() {
    StringBuilder sb = new StringBuilder();
    sb.append("JAVA_HOME path: " + System.getProperty("java.home") + "<br>");
    sb.append("Java Vendor: " + System.getProperty("java.vendor") + "<br");
    sb.append("Operating System: " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " "
        + System.getProperty("os.version") + "<br>");
    sb.append("Operating User: " + System.getProperty("user.name") + "<br>");
    sb.append("Avaliable Processors: " + Runtime.getRuntime().availableProcessors() + "<br>");
    sb.append("Runtime Version: " + Runtime.version() + "<br>");
    sb.append("Free Memory: " + Runtime.getRuntime().freeMemory() + "<br>");
    sb.append("Max Memory: " + Runtime.getRuntime().maxMemory() + "<br>");
    sb.append("Total Memory: " + Runtime.getRuntime().totalMemory() + "<br>");
    sb.append("System Env: " + System.getenv().toString() + "<br>");
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
