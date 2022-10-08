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

import java.awt.image.BufferedImage;

/**
 * The Main BlurHash Extern Class that provides
 * a High Level Method to directly interface with the
 * Child Low Level Class
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cloudspin.lib.Blur
 * @since 1.0
 */
public class BlurHash implements Blur {

    /**
     * @param image
     * @param _x
     * @param _y
     * @param otherParams
     * @return BufferedImage
     */
    @Override
    public BufferedImage blur(BufferedImage image, int _x, int _y, Object... otherParams) {
        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        String hash = BlurHashChild.enc(pixels, image.getWidth(), image.getHeight(), _x, _y);
        double p = 1.2d;
        if (otherParams != null) {
            if (otherParams[0] instanceof Double) {
                if ((Double) otherParams[0] > 0) {
                    p = (Double) otherParams[0];
                }
            }
        }
        int[] dec = BlurHashChild.dec(hash, image.getWidth(), image.getHeight(), p);
        BufferedImage res = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        res.setRGB(0, 0, image.getWidth(), image.getHeight(), dec, 0, image.getWidth());
        return res;
    }

}
