package debug;

import javax.swing.*;

import project.Manager;

import java.awt.*;

public class DebugView extends JScrollPane {
  private JEditorPane text;

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

  private static String getMemoryUsage() {
    return "Memory (mB): " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
        + " / " + Runtime.getRuntime().totalMemory() / 1024 / 1024;
  }
}
