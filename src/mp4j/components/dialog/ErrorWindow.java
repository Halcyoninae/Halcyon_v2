package mp4j.components.dialog;

import javax.swing.*;

import mp4j.Manager;

import java.awt.*;

public class ErrorWindow extends JFrame implements Runnable {
  public ErrorWindow(String content) {
    super(Manager.DIALOG_ERROR_WIN_TITLE);
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
