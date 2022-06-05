package com.jackmeng.app.connections.resource;

import javax.swing.ImageIcon;

import com.jackmeng.debug.Debugger;

public class ResourceDistributor {
  public ImageIcon getFromAsImageIcon(String path) {
    try {
      return new ImageIcon(java.util.Objects.requireNonNull(getClass().getResource(path)));
    } catch (NullPointerException e) {
      Debugger
          .log("Resource Distributor was unable to fetch the expected path: " + path + "\nResorted to raw fetching...");
      return new ImageIcon(path);
    }
  }
}
