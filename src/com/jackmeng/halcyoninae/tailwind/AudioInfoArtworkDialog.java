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

package com.jackmeng.halcyoninae.tailwind;

import com.jackmeng.halcyoninae.halcyon.utils.CLIStyles;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.TConstr;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Jack Meng
 * @since 3.3
 */
public class AudioInfoArtworkDialog extends JFrame implements Runnable {
    private final JPanel drawable;

    private final transient BufferedImage img;
    private final transient AudioInfo info;

    public AudioInfoArtworkDialog(AudioInfo info) {
        this.info = info;
        this.img = info.getArtwork();
        setTitle("Halcyon ~ Artwork Viewer | " + img.getWidth() + "x" + img.getHeight());
        setIconImage(img);


        setPreferredSize(new Dimension(600, 400));
        setSize(getPreferredSize());

        drawable = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, (drawable.getWidth() - img.getWidth()) / 2,
                    (drawable.getHeight() - img.getHeight()) / 2, null);
                g.dispose();
            }
        };

        JScrollPane jsp = new JScrollPane(drawable);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        add(jsp);

    }


    /**
     * @return AudioInfo
     */
    public AudioInfo getAudioInfo() {
        return info;
    }


    /**
     * @return BufferedImage
     */
    public BufferedImage getViewportArtwork() {
        return img;
    }

    @Override
    public void run() {
        Debugger.alert(new TConstr(CLIStyles.BLUE_TXT, "New Artwork Viewport being dispatched... Taking my time"));
        pack();
        setVisible(true);
    }
}