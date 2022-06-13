package com.jackmeng.app.constant;

import com.jackmeng.app.components.bottompane.BPTabs;
import com.jackmeng.app.components.bottompane.BottomPane;
import com.jackmeng.app.components.bottompane.tabs.FileView;
import com.jackmeng.app.components.toppane.layout.ButtonControlTP;
import com.jackmeng.app.components.toppane.layout.InfoViewTP;
import com.jackmeng.app.connections.resource.ResourceDistributor;
import com.jackmeng.audio.Player;
import com.jackmeng.debug.DebugView;

public class Global {
  private Global() {}
  public static FileView f = new FileView();
  public static BottomPane bp = new BottomPane();
  public static final ResourceDistributor rd = new ResourceDistributor();
  public static ButtonControlTP bctp = new ButtonControlTP();
  public static InfoViewTP ifp = new InfoViewTP();
  public static BPTabs debuggerTab = new DebugView();
  public static Player player = new Player();
}
