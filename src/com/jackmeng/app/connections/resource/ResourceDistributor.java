package com.jackmeng.app.connections.resource;

import com.jackmeng.debug.Debugger;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Retrieves resources from the binary resource folder.
 *
 * This resource folder is not located externally and is packed in during
 * compilation to the generated binary.
 *
 * This class should not be confused with {@link com.jackmeng.app.connections.properties.ResourceFolder}
 * as that class handles external resources, while this class handles internal resources.
 *
 * @author Jack Meng
 * @since 2.1
 */
public class ResourceDistributor {

  /**
   * Gets an ImageIcon from the resource folder.
   * @param path The path to the image
   * @return ImageIcon The image icon
   */
  public ImageIcon getFromAsImageIcon(String path) {
    try {
      return new ImageIcon(
        java.util.Objects.requireNonNull(getClass().getResource(path))
      );
    } catch (NullPointerException e) {
      Debugger.log(
        "Resource Distributor [IMAGE] was unable to fetch the expected path: " +
        path +
        "\nResorted to raw fetching..."
      );
      return new ImageIcon(path);
    }
  }

  /**
   * Gets a standard file from the resource folder.
   * @param path The path to the file
   * @return File The file
   */
  public File getFromAsFile(String path) {
    try {
      return new File(
        java.util.Objects.requireNonNull(getClass().getResource(path)).getFile()
      );
    } catch (NullPointerException e) {
      Debugger.log(
        "Resource Distributor [FILE] was unable to fetch the expected path: " +
        path +
        "\nResorted to raw fetching..."
      );
      return new File(path);
    }
  }

  public URL getFromAsURL(String path) {
    try {
      return java.util.Objects.requireNonNull(getClass().getResource(path));
    } catch (NullPointerException e) {
      Debugger.log(
        "Resource Distributor [URL] was unable to fetch the expected path: " +
        path +
        "\nResorted to raw fetching..."
      );
      try {
        return new URL(path);
      } catch (MalformedURLException e1) {
        e1.printStackTrace();
      }
    }
    return null;
  }
}
