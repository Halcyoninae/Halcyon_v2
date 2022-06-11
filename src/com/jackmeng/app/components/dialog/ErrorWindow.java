package com.jackmeng.app.components.dialog;

import javax.swing.*;

import com.jackmeng.app.Global;
import com.jackmeng.app.constant.Manager;

import java.awt.*;

public class ErrorWindow extends JFrame implements Runnable {
  public ErrorWindow(String content) {
    super(Manager.DIALOG_ERROR_WIN_TITLE);
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    setPreferredSize(new Dimension(Manager.DIALOG_ERROR_MIN_WIDTH, Manager.DIALOG_ERROR_MIN_HEIGHT));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);
    JTextArea jt = new JTextArea(content);
    jt.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(jt);
    scrollPane.setPreferredSize(new Dimension(Manager.DIALOG_ERROR_MIN_WIDTH, Manager.DIALOG_ERROR_MIN_HEIGHT));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    getContentPane().add(scrollPane);
  }

  @Override
  public void run() {
    this.pack();
    this.setAlwaysOnTop(true);
    this.setVisible(true);
  }
}
