package mp4j;

import mp4j.components.tabs.FileView;
import mp4j.components.toppane.layout.ButtonControlTP;
import mp4j.components.toppane.layout.InfoViewTP;
import mp4j.connections.resource.ResourceDistributor;

public interface Global {

  public static FileView f = new FileView();
  public static final ResourceDistributor rd = new ResourceDistributor();
  public static ButtonControlTP bctp = new ButtonControlTP();
  public static InfoViewTP ifp = new InfoViewTP();
}
