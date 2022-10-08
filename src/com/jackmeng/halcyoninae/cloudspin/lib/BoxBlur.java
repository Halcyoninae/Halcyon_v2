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

package com.jackmeng.halcyoninae.cloudspin.lib;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import com.jackmeng.halcyoninae.cloudspin.SpeedStyle;

/**
 * @author Jack Meng
 * @since 3.2
 */
public class BoxBlur implements Blur {


    /**
     * @param image
     * @param _x
     * @param _y
     * @param otherParams
     * @return BufferedImage
     */
    @Override
    public BufferedImage blur(BufferedImage image, int _x, int _y, Object... otherParams) {
        BufferedImage mod = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        if (otherParams[0].equals(SpeedStyle.GENERAL)) {
            for (int row = 0; row < image.getHeight(); row++) {
                for (int col = 0; col < image.getWidth(); col++) {
                    int x = col - _x;
                    int y = row - _y;
                    int count = 0;
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for (int i = 0; i < _x * 2 + 1; i++) {
                        for (int j = 0; j < _y * 2 + 1; j++) {
                            if (x + i < 0 || x + i >= image.getWidth() || y + j < 0 || y + j >= image.getHeight()) {
                                continue;
                            }
                            Color c = new Color(image.getRGB(x + i, y + j));
                            r += c.getRed();
                            g += c.getGreen();
                            b += c.getBlue();
                            count++;
                        }
                    }
                    Color c = new Color(r / count, g / count, b / count);
                    mod.setRGB(col, row, c.getRGB());
                }
            }
        } else if (otherParams[0].equals(SpeedStyle.SPEED)) {
            int radius = (int) otherParams[1];
            float weight = radius * radius / ((float) radius);
            float[] kernel = new float[radius * radius];
            for (int i = 0; i < radius * radius; i++) {
                kernel[i] = weight;
            }
            Kernel k = new Kernel(radius, radius, kernel);
            // fill the edges with blurring as well
            RenderingHints r = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            r.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR));
            ConvolveOp op = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, r);
            op.createCompatibleDestRaster(image.getRaster());
            mod = op.filter(image, mod);
        }
        return mod;

    }
}
