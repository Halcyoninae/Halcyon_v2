package com.jackmeng.music;

import com.jackmeng.debug.DebugView;
import com.jackmeng.music.components.bottompane.BPTabs;
import com.jackmeng.music.components.bottompane.tabs.FileView;
import com.jackmeng.music.components.toppane.layout.ButtonControlTP;
import com.jackmeng.music.components.toppane.layout.InfoViewTP;
import com.jackmeng.music.connections.resource.ResourceDistributor;

public interface Global {

  public static FileView f = new FileView();
  public static final ResourceDistributor rd = new ResourceDistributor();
  public static ButtonControlTP bctp = new ButtonControlTP();
  public static InfoViewTP ifp = new InfoViewTP();
  public static BPTabs debuggerTab = new DebugView();
}
