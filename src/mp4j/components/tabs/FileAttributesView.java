package mp4j.components.tabs;

import javax.swing.*;

import global.Pair;
import mp4j.Global;
import mp4j.Manager;
import mp4j.components.BPTabs;

import java.awt.*;
import java.util.Map;

public class FileAttributesView extends JScrollPane implements BPTabs {
  private JEditorPane editor;

  public FileAttributesView() {
    super();
    editor = new JEditorPane();
    editor.setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    editor.setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    editor.setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    editor.setEditable(false);
    editor.setContentType("text/html");
    editor.setText(getFileViewHash());
    setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    getViewport().setPreferredSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    getViewport().setMaximumSize(new Dimension(Manager.FILEVIEW_MAX_WIDTH, Manager.FILEVIEW_MAX_HEIGHT));
    getViewport().setMinimumSize(new Dimension(Manager.FILEVIEW_MIN_WIDTH, Manager.FILEVIEW_MIN_HEIGHT));
    setViewportView(editor);
    new Thread(() -> {
      while (true) {
        editor.setText(getFileViewHash());
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private static String getFileViewHash() {
    StringBuilder sb = new StringBuilder();
    sb.append("<html><p>");
    Map<String, Pair<String, String[]>> files = Global.f.getFolders();

    for (String key : files.keySet()) {
      sb.append(key + "=" + files.get(key).getFirst() + "<br>");
    }
    sb.append("</p></html>");
    return sb.toString();
  }

  @Override
  public String getTabName() {
    return Manager.ATTRIBUTES_DEFAULT_TAB_NAME;
  }

  @Override
  public String getTabToolTip() {
    return Manager.ATTRIBUTES_DEFAULT_TAB_TOOLTIP;
  }

  @Override
  public JComponent getTabContent() {
    return this;
  }
}
