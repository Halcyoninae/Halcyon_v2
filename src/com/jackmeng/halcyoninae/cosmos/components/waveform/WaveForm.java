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

package com.jackmeng.halcyoninae.cosmos.components.waveform;

import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.utils.Wrapper;
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
                    g2.setBackground(ColorManager.ONE_DARK_BG);
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
        SwingUtilities.invokeLater(() -> make(samples));;
    }
}
