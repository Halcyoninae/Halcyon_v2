/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
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
