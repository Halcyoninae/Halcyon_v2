package com.jackmeng.app;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import com.jackmeng.ProjectManager;
import com.jackmeng.app.components.BigContainerTest;
import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.app.components.bbloc.BBlocView;
import com.jackmeng.app.components.bbloc.buttons.AddFolder;
import com.jackmeng.app.components.bbloc.buttons.GenericWebsiteLinker;
import com.jackmeng.app.components.bbloc.buttons.RefreshFileView;
import com.jackmeng.app.components.bottompane.BPTabs;
import com.jackmeng.app.components.bottompane.BottomPane;
import com.jackmeng.app.components.bottompane.tabs.FileAttributesView;
import com.jackmeng.app.components.toppane.TopPane;

public class Main {
  public static BigContainerTest bgt;

  public static void main(String... args) {
    new ThreadedScheduler();
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
    Global.ifp.addInfoViewUpdateListener(Global.bctp);
    JSplitPane bottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    bottom.setMinimumSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    bottom.setPreferredSize(new Dimension(Manager.MIN_WIDTH, Manager.MIN_HEIGHT / 2));
    bottom.setMaximumSize(new Dimension(Manager.MAX_WIDTH, Manager.MAX_HEIGHT / 2));
    ArrayList<BBlocButton> bb = new ArrayList<>();
    bb.add(new AddFolder());
    bb.add(new RefreshFileView());
    bb.add(GenericWebsiteLinker.getButton(ProjectManager.SPOTIFY_PAGE, Manager.SPOTIFYPAGE_BUTTON_TOOLTIP,
        Global.rd.getFromAsImageIcon(Manager.SPOTIFY_LOGO_LIGHT)));
    bb.add(GenericWebsiteLinker.getButton(ProjectManager.YOUTUBE_PAGE, Manager.YOUTUBEPAGE_BUTTON_TOOLTIP,
        Global.rd.getFromAsImageIcon(Manager.YOUTUBE_LOGO_RED)));
    bb.add(GenericWebsiteLinker.getButton(ProjectManager.SOUNDCLOUD_PAGE, Manager.SOUNDCLOUD_BUTTON_TOOLTIP,
        Global.rd.getFromAsImageIcon(Manager.SOUNDCLOUD_LOGO_ORANGE)));
    bb.add(GenericWebsiteLinker.getButton(ProjectManager.PROJECT_GITHUB_PAGE, Manager.PROJECTPAGE_BUTTON_TOOLTIP,
        Global.rd.getFromAsImageIcon(Manager.GITHUB_LOGO_LIGHT)));
    BBlocView b = new BBlocView();
    b.addBBlockButtons(bb.toArray(new BBlocButton[bb.size()]));
    ArrayList<BPTabs> tabs = new ArrayList<>();
    tabs.add(Global.f);
    tabs.add(Global.debuggerTab);
    tabs.add(new FileAttributesView());
    Global.f.getTree().addTreeSelectionListener(Global.ifp);
    BottomPane bp = new BottomPane(tabs);
    bottom.add(b);
    bottom.add(bp);

    JSplitPane m = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tp, bottom);
    bgt = new BigContainerTest(m);
    bgt.run();
  }
}
