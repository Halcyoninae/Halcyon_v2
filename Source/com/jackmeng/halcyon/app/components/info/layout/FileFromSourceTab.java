package com.jackmeng.halcyon.app.components.info.layout;

import com.jackmeng.halcyon.app.components.info.InformationTab;
import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileFromSourceTab extends JScrollPane implements InformationTab {
  private JEditorPane text;
  private String tabName = "";

  public FileFromSourceTab(String tabName, String textTab) {
    setPreferredSize(new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));
    setFocusable(true);

    this.tabName = tabName;

    text = new JEditorPane();
    text.setEditable(false);
    text.setContentType("text/html");
    text.setText(textTab);
    text.setCaretPosition(0);

    getViewport().add(text);
  }

  public static String getContent(String file) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    } catch (Exception e) {
      ResourceFolder.dispatchLog(e);
    }
    return sb.toString();
  }

  @Override
  public String getName() {
    return tabName;
  }

  @Override
  public String getToolTip() {
    return "View Legal Information regarding this program.";
  }

  @Override
  public JComponent getComponent() {
    return this;
  }
}
