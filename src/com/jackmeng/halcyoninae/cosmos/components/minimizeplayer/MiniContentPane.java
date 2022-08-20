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

package com.jackmeng.halcyoninae.cosmos.components.minimizeplayer;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.OverlayLayout;

import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.connections.properties.ProgramResourceManager;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage.Directional;
import com.jackmeng.halcyoninae.tailwind.audioinfo.AudioInfo;

/**
 * This class holds all of the components to the main
 * MiniPlayer frame.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.minimizeplayer.MiniPlayer
 * @since 3.2
 */
public class MiniContentPane extends JPanel implements InfoViewUpdateListener {
    private final JPanel bgPanel;
    private final JPanel fgPanel;
    private final JPanel topPanel;
    private final JPanel progressPanel;
    private final JLabel mainLabel;
    private final JLabel artLabel;
    private final JProgressBar progressBar;
    private transient AudioInfo info;
    private boolean fDrawn = true;
    private transient BufferedImage bg;

    public MiniContentPane() {
        setPreferredSize(
                new Dimension(MiniPlayerManager.MINI_PLAYER_MIN_WIDTH, MiniPlayerManager.MINI_PLAYER_MIN_HEIGHT));
        setLayout(new OverlayLayout(this));
        info = new AudioInfo();
        bgPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (info.hasArtwork() || !fDrawn && bg != null) {
                    Debugger.warn("Found a new artwork!@MINIPLAYER" + " | Composite: " + Float
                            .parseFloat(
                                    ExternalResource.pm.get(ProgramResourceManager.KEY_MINI_PLAYER_DEFAULT_BG_ALPHA)));
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Float
                            .parseFloat(
                                    ExternalResource.pm.get(ProgramResourceManager.KEY_MINI_PLAYER_DEFAULT_BG_ALPHA))));
                    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                    g2.drawImage(
                            bg,
                            0, 0, null);
                    g2.dispose();
                    fDrawn = true;
                } else {
                    Debugger.warn("Reusing last artwork!");
                }
                g.dispose();
            }

            @Override
            public boolean isOptimizedDrawingEnabled() {
                return false;
            }
        };
        bgPanel.setPreferredSize(getPreferredSize());
        bgPanel.setOpaque(false);
        bgPanel.setIgnoreRepaint(true);
        bgPanel.setDoubleBuffered(false);

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height - 15));
        topPanel.setLayout(new GridLayout(1, 2));
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        topPanel.setOpaque(false);

        mainLabel = new JLabel();
        mainLabel.setText(getLabelString());

        artLabel = new JLabel(
                new ImageIcon(
                        DeImage.createRoundedBorder(DeImage.resizeNoDistort(info.getArtwork(), 128, 128), 10, null)));
        artLabel.setDoubleBuffered(true);

        topPanel.add(artLabel);
        topPanel.add(mainLabel);

        progressPanel = new JPanel();
        progressPanel.setPreferredSize(new Dimension(getPreferredSize().width, 15));

        fgPanel = new JPanel();
        fgPanel.setPreferredSize(getPreferredSize());
        fgPanel.setLayout(new BorderLayout());
        fgPanel.setOpaque(false);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(progressPanel.getPreferredSize());
        progressBar.setMaximum(500);
        progressBar.setMinimum(0);
        progressBar.setOpaque(false);
        progressBar.setBackground(new Color(0, 0, 0, 50));
        progressBar.setForeground(ColorManager.MAIN_FG_THEME);
        progressPanel.add(progressBar);

        fgPanel.add(topPanel, BorderLayout.NORTH);
        fgPanel.add(progressPanel, BorderLayout.SOUTH);

        add(bgPanel);
        add(fgPanel);
    }

    private void __refresh_draw_bg_img_() {
        BufferedImage img = null;
        if (info.hasArtwork()) {
            img = MiniDeImage.blurHash(DeImage.resize(info.getArtwork(), getWidth(), getHeight()), 3, 4);
        }
        bg = img;
    }

    /**
     * @return String
     */
    private String getLabelString() {
        return "<html><p style=\"color:" + ColorManager.MAIN_FG_STR + ";font-size:12px\"><b>"
                + (info.getTag(AudioInfo.KEY_MEDIA_TITLE).length() > 28
                        ? info.getTag(AudioInfo.KEY_MEDIA_TITLE).substring(0, 28) + "..."
                        : info.getTag(AudioInfo.KEY_MEDIA_TITLE))
                + "</b></p><br><center><p>" + info.getTag(AudioInfo.KEY_MEDIA_ARTIST) + "</p></center></html>";
    }

    private void scheduleRedraw() {
        __refresh_draw_bg_img_();
        bgPanel.repaint(50);
        mainLabel.setText(getLabelString());
        artLabel.setIcon(
                new ImageIcon(
                        DeImage.createRoundedBorder(DeImage.resizeNoDistort(info.getArtwork(), 128, 128), 10, null)));
    }

    /**
     * @param info
     */
    @Override
    public void infoView(AudioInfo info) {
        this.info = info;
        scheduleRedraw();
    }
}
