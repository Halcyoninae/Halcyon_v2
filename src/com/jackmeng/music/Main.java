package com.jackmeng.music;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.jackmeng.debug.DebugView;
import com.jackmeng.music.components.BBlocView;
import com.jackmeng.music.components.BPTabs;
import com.jackmeng.music.components.BigContainerTest;
import com.jackmeng.music.components.BottomPane;
import com.jackmeng.music.components.tabs.FileAttributesView;
import com.jackmeng.music.components.toppane.TopPane;

public class Main {
  static {
    System.setProperty("file.encoding", "UTF-8");
    System.setProperty("sun.jnu.encoding", "UTF-8");
    System.setProperty("flatlaf.useJetBrainsCustomDecorations", "true");
    UIManager.put("FileChooser.readOnly", true);
    try {
      UIManager.setLookAndFeel(FlatArcDarkIJTheme.class.getName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String... args) {

    /*
     * STUB PANEL:
     * JPanel one = new JPanel();
     * one.setOpaque(true);
     * one.setSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
     * one.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT /
     * 2));
     * one.setMinimumSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
     * one.setMaximumSize(new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT / 2));
     * one.setBackground(Color.WHITE);
     */
    TopPane tp = new TopPane(Global.ifp, Global.bctp);

    JSplitPane bottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    bottom.setMinimumSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    bottom.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    bottom.setMaximumSize(new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT / 2));
    BBlocView b = new BBlocView();
    ArrayList<BPTabs> tabs = new ArrayList<>();
    tabs.add(Global.f);
    tabs.add(new DebugView());
    tabs.add(new FileAttributesView());
    BottomPane bp = new BottomPane(tabs);
    bottom.add(b);
    bottom.add(bp);

    JSplitPane m = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tp, bottom);

    new BigContainerTest(m).run();
  }
}
