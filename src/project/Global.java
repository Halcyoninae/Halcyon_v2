package project;

import project.components.tabs.FileView;
import project.connections.resource.ResourceDistributor;

public final class Global {
  private Global() {}
  
  public static FileView f = new FileView();
  public static final ResourceDistributor rd = new ResourceDistributor();
}
