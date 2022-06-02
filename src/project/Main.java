package project;

import com.formdev.flatlaf.FlatDarkLaf;

import project.components.BBlocView;
import project.components.BigContainerTest;
import project.components.FileView;

import java.awt.*;

import javax.swing.*;

public class Main {
  static {
    System.setProperty("file.encoding", "UTF-8");
    System.setProperty("sun.jnu.encoding", "UTF-8");
    System.setProperty("flatlaf.useJetBrainsCustomDecorations", "true");
    UIManager.put("FileChooser.readOnly", true);
    try {
      UIManager.setLookAndFeel(FlatDarkLaf.class.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String... args) {

    JPanel one = new JPanel();
    one.setOpaque(true);
    one.setSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    one.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    one.setMinimumSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    one.setMaximumSize(new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT / 2));
    one.setBackground(Color.WHITE);

    JSplitPane bottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    bottom.setMinimumSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    bottom.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    bottom.setMaximumSize(new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT / 2));
    bottom.add(Global.f);
    BBlocView b = new BBlocView();
    bottom.add(b);
    JSplitPane m = new JSplitPane(JSplitPane.VERTICAL_SPLIT, one, bottom);

    new BigContainerTest(m).run();
  }
}
