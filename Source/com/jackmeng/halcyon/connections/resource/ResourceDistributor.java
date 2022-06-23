/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.halcyon.connections.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

import com.jackmeng.halcyon.debug.Debugger;

import java.io.InputStream;

/**
 * Retrieves resources from the binary resource folder.
 *
 * This resource folder is not located externally and is packed in during
 * compilation to the generated binary.
 *
 * This class should not be confused with {@link com.jackmeng.halcyon.connections.properties.ResourceFolder}
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
          java.util.Objects.requireNonNull(getClass().getResource(path)).getFile());
    } catch (NullPointerException e) {
      Debugger.log(
          "Resource Distributor [FILE] was unable to fetch the expected path: " +
              path +
              "\nResorted to raw fetching...");
      return new File(path);
    }
  }

  /**
   * Similar to other methods but returns as {@link java.net.URL}
   * @param path
   * @return
   */
  public URL getFromAsURL(String path) {
    try {
      return java.util.Objects.requireNonNull(getClass().getResource(path));
    } catch (NullPointerException e) {
      Debugger.log(
          "Resource Distributor [URL] was unable to fetch the expected path: " +
              path +
              "\nResorted to raw fetching...");
      try {
        return new URL(path);
      } catch (MalformedURLException e1) {
        e1.printStackTrace();
      }
    }
    return null;
  }

  /**
   * Similar to other methods but returns as {@link java.io.InputStream}
   * @param path
   * @return
   */
  public InputStream getFromAsStream(String path) {
    try {
      return java.util.Objects.requireNonNull(getClass().getResourceAsStream(path));
    } catch (NullPointerException e) {
      Debugger.log(
        "Resource Distributor [STREAM] was unable to fetch the expected path: " +
        path +
        "\nResorted to raw fetching..."
      );
      try {
        return new FileInputStream(path);
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      }
    }
    return null;
  }
}
