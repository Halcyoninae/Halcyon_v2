package project;

import project.components.tabs.FileView;
import project.components.toppane.layout.ButtonControlTP;
import project.components.toppane.layout.InfoViewTP;
import project.connections.resource.ResourceDistributor;

public final class Global {
  private Global() {
  }

  public static FileView f = new FileView();
  public static final ResourceDistributor rd = new ResourceDistributor();
  public static ButtonControlTP bctp = new ButtonControlTP();
  public static InfoViewTP ifp = new InfoViewTP();
}
