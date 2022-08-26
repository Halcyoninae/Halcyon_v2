/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.halcyon.connections.resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Retrieves resources from the binary resource folder.
 * <p>
 * This resource folder is not located externally and is packed in during
 * compilation to the generated binary.
 * <p>
 * This class should not be confused with {@link com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource}
 * as that class handles external resources, while this class handles internal resources.
 *
 * @author Jack Meng
 * @since 2.1
 */
public class ResourceDistributor {

    /**
     * Gets an ImageIcon from the resource folder.
     *
     * @param path The path to the image
     * @return ImageIcon The image icon
     */
    public ImageIcon getFromAsImageIcon(String path) {
        try {
            return new ImageIcon(
                    java.util.Objects.requireNonNull(getClass().getResource(path))
            );
        } catch (NullPointerException e) {

            return new ImageIcon(path);
        }
    }

    /**
     * Gets a standard file from the resource folder.
     *
     * @param path The path to the file
     * @return File The file
     */
    public File getFromAsFile(String path) {
        try {
            return new File(
                    java.util.Objects.requireNonNull(getClass().getResource(path)).getFile());
        } catch (NullPointerException e) {

            return new File(path);
        }
    }

    /**
     * Similar to other methods but returns as {@link java.net.URL}
     *
     * @param path
     * @return
     */
    public URL getFromAsURL(String path) {
        try {
            return java.util.Objects.requireNonNull(getClass().getResource(path));
        } catch (NullPointerException e) {

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
     *
     * @param path
     * @return
     */
    public InputStream getFromAsStream(String path) {
        try {
            return java.util.Objects.requireNonNull(getClass().getResourceAsStream(path));
        } catch (NullPointerException e) {

            try {
                return new FileInputStream(path);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Retrieve a resource as a BufferedImage
     *
     * @param path The path to the suspected resource
     * @return The BufferedImage or null if not found
     */
    public BufferedImage getFromAsBufferedImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {

        }
        return null;
    }
}
