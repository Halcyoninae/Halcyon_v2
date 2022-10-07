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

package com.jackmeng.halcyoninae.cosmos.components.minimizeplayer;

import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.ExternalResource;
import com.jackmeng.halcyoninae.halcyon.utils.Numerical;
import com.jackmeng.halcyoninae.halcyon.utils.ProgramResourceManager;
import com.jackmeng.halcyoninae.tailwind.AudioInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final JLabel mainLabel;
    private final JLabel artLabel;
    private final JProgressBar progressBar;
    private transient AudioInfo info;
    private boolean fDrawn = true;
    private transient ExecutorService timeKeeper;
    private transient BufferedImage bg;

    public MiniContentPane() {
        setPreferredSize(
            new Dimension(MiniPlayerManager.MINI_PLAYER_MIN_WIDTH, MiniPlayerManager.MINI_PLAYER_MIN_HEIGHT));
        setLayout(new OverlayLayout(this));
        setOpaque(false);
        info = new AudioInfo();
        bgPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                if (info.hasArtwork() || !fDrawn && bg != null) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, Float
                        .parseFloat(ExternalResource.pm
                            .get(ProgramResourceManager.KEY_MINI_PLAYER_DEFAULT_BG_ALPHA)) > 0.5F ? 1.0F - Float
                        .parseFloat(
                            ExternalResource.pm.get(
                                ProgramResourceManager.KEY_MINI_PLAYER_DEFAULT_BG_ALPHA))
                        : Float
                        .parseFloat(
                            ExternalResource.pm.get(
                                ProgramResourceManager.KEY_MINI_PLAYER_DEFAULT_BG_ALPHA))));
                    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                    g2.drawImage(
                        bg,
                        getInsets().left, getInsets().top, null);
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
        bgPanel.setIgnoreRepaint(false);
        bgPanel.setDoubleBuffered(false);

        JPanel topPanel = new JPanel();
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

        topPanel.add(artLabel);
        topPanel.add(mainLabel);

        JPanel progressPanel = new JPanel();
        progressPanel.setPreferredSize(new Dimension(getPreferredSize().width, 15));

        JPanel fgPanel = new JPanel();
        fgPanel.setPreferredSize(getPreferredSize());
        fgPanel.setLayout(new BorderLayout());
        fgPanel.setOpaque(false);

        progressBar = new JProgressBar();
        progressBar.setPreferredSize(progressPanel.getPreferredSize());
        progressBar.setMaximum(250);
        progressBar.setMinimum(0);
        progressBar.setValue(0);
        progressBar.setBackground(new Color(1, 1, 1, 130));
        progressBar.setOpaque(false);
        progressBar.setAutoscrolls(false);
        progressBar.setBorderPainted(false);
        progressBar.setForeground(ColorManager.MAIN_FG_THEME);
        progressPanel.add(progressBar);

        fgPanel.add(topPanel, BorderLayout.NORTH);
        fgPanel.add(progressPanel, BorderLayout.SOUTH);

        add(bgPanel);
        add(fgPanel);
        _init_time();
    }

    private void _init_time() {
        timeKeeper = Executors.newFixedThreadPool(1);
        timeKeeper.execute(() -> {
            while (true) {
                if (this.isShowing() || this.isVisible()) {
                    progressBar.setValue((int) (Numerical.__safe_divide(
                        Global.player.getStream().getPosition() * (double) progressBar
                            .getMaximum(),
                        Global.player.getStream().getLength())));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // IGNORED.
                    }
                }
            }
        });
    }

    private void __refresh_draw_bg_img_() {
        BufferedImage img = null;
        if (info.hasArtwork()) {
            img = MiniDeImage.blurHash(DeImage.resize(info.getArtwork(), (int) bgPanel.getPreferredSize().getWidth(),
                (int) bgPanel.getPreferredSize().getHeight()), 4, 4);
        }
        bg = img;
        if (bg != null) {
            bg.setAccelerationPriority(1F);
        }
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
        repaint(40);
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
        if (this.isVisible() || this.isShowing() || this.getParent().isVisible()) {
            scheduleRedraw();
        }
    }
}
