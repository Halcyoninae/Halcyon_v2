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

package com.jackmeng.halcyoninae.cloudspin.lib.swing;

import com.twelvemonkeys.image.ConvolveWithEdgeOp;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.Kernel;

public class StdBlurLayer extends LayerUI<Component> {
    private transient BufferedImageOp oImageOp;

    public StdBlurLayer(int blur, RenderingHints renderer, int edgeNotation) {
        float[] matrix = new float[blur * blur];
        float frac = 1.0F / (blur * blur);
        for (int i = 0; i < blur * blur; i++) {
            matrix[i] = frac;
        }
        oImageOp = new ConvolveWithEdgeOp(new Kernel(blur, blur, matrix), edgeNotation, renderer);
    }

    /**
     * @param g
     * @param comp
     */
    @Override
    public void paint(Graphics g, JComponent comp) {
        if (comp.getWidth() == 0 || comp.getHeight() == 0)
            return;

        BufferedImage img = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D ig2 = img.createGraphics();
        ig2.setClip(g.getClip());
        super.paint(ig2, comp);
        ig2.dispose();
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, oImageOp, 0, 0);
        g2.dispose();
        g.dispose();
    }
}
