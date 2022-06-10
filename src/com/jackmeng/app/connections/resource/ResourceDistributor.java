package com.jackmeng.app.connections.resource;

import java.io.File;

import javax.swing.ImageIcon;

import com.jackmeng.debug.Debugger;

public class ResourceDistributor {
  public ImageIcon getFromAsImageIcon(String path) {
    try {
      return new ImageIcon(java.util.Objects.requireNonNull(getClass().getResource(path)));
    } catch (NullPointerException e) {
      Debugger
          .log("Resource Distributor [IMAGE] was unable to fetch the expected path: " + path + "\nResorted to raw fetching...");
      return new ImageIcon(path);
    }
  }

  public File getFromAsFile(String path) {
    try {
      return new File(java.util.Objects.requireNonNull(getClass().getResource(path)).getFile());
    } catch (NullPointerException e) {
      Debugger
          .log("Resource Distributor [FILE] was unable to fetch the expected path: " + path + "\nResorted to raw fetching...");
      return new File(path);
    }
  }
}
