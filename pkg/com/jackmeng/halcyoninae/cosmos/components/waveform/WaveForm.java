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

package com.jackmeng.halcyoninae.cosmos.components.waveform;

import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.tailwind.TailwindListener;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.Map;

/**
 * A WaveForm main panel.
 * <p>
 * 3.3 : Optimized the heck out of the algorithms
 * used to draw thus reducing overhead and memory
 * usage.
 *
 * @author Jack Meng
 * @since 3.2
 */
public class WaveForm extends JPanel implements TailwindListener.FrameBufferListener {
    private static final Map<RenderingHints.Key, Object> renderinghints = new HashMap<>();

    static {
        renderinghints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    private final transient Object lock = new Object();
    private byte[] samples;
    private Path2D.Float path = new Path2D.Float();
    private int s_valid;
    private boolean fakeVis = false;

    public WaveForm() {
        setPreferredSize(new Dimension(WaveFormManager.MIN_WIDTH, WaveFormManager.MIN_HEIGHT));
        setOpaque(false);
        setDoubleBuffered(true);
    }

    /**
     * @param vis
     */
    public void setVisibility(boolean vis) {
        fakeVis = vis;
    }

    /**
     * @return boolean
     */
    public boolean isFakeVisible() {
        return fakeVis;
    }

    /**
     * DOES NOT REPAINT THE FRAME
     * <p>
     * REPAINT MUST BE CALLED SEPARATELY
     *
     * @param samples
     */
    public void make(byte[] samples) {
        this.samples = samples;
    }

    /**
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        if (this.fakeVis) {
            if (Global.player.getStream().getAudioFormatAbsolute() != null) {

                s_valid /= 4;
                Path2D.Float main = path;
                float avg = 0f;
                float hd2 = getHeight() / 2f;
                final int chnls = Global.player.getStream().getAudioFormatAbsolute().getChannels();
                int i = 0;
                while (i < chnls && i < s_valid)
                    avg += samples[i++];
                avg /= chnls;
                main.reset();
                main.moveTo(0, hd2 - avg * hd2);
                int fvalid = s_valid / chnls;
                for (int ch, frame = 0; i < s_valid; frame++) {
                    avg = 0f;
                    for (ch = 0; ch < chnls; ch++) {
                        avg += samples[i++];
                    }
                    avg /= chnls;
                    main.lineTo(
                        (float) frame / fvalid * this.getWidth(), hd2 - avg * hd2);
                }
                path = main;

                Graphics2D g2 = (Graphics2D) g;

                synchronized (lock) {
                    g2.setBackground(ColorManager.MAIN_BG_THEME);
                    g2.clearRect(0, 0, this.getWidth(), this.getHeight());
                    g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setStroke(new BasicStroke(3));
                    g2.addRenderingHints(renderinghints);
                    g2.setPaint(ColorManager.MAIN_FG_THEME);
                    g2.draw(path);
                }
                g2.dispose();
            }
        }
    }

    /**
     * @param samples
     */
    @Override
    public void frameUpdate(byte[] samples) {
        SwingUtilities.invokeLater(() -> make(samples));
    }
}
