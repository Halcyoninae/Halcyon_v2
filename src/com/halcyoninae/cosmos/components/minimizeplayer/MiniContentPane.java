/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
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

package com.halcyoninae.cosmos.components.minimizeplayer;

import com.halcyoninae.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.halcyoninae.halcyon.connections.properties.ResourceFolder;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.ProgramResourceManager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.DeImage;
import com.halcyoninae.halcyon.utils.DeImage.Directional;
import com.halcyoninae.tailwind.AudioInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * This class holds all of the components to the main
 * MiniPlayer frame.
 *
 * @see com.halcyoninae.cosmos.components.minimizeplayer.MiniPlayer
 *
 * @author Jack Meng
 * @since 3.2
 */
public class MiniContentPane extends JPanel implements InfoViewUpdateListener {
  private JPanel bgPanel, fgPanel, topPanel, progressPanel;
  private JLabel mainLabel, artLabel;
  private JProgressBar progressBar;
  private transient AudioInfo info;
  private boolean fDrawn = true;

  public MiniContentPane() {
    setPreferredSize(new Dimension(MiniPlayerManager.MINI_PLAYER_MIN_WIDTH, MiniPlayerManager.MINI_PLAYER_MIN_HEIGHT));
    setLayout(new OverlayLayout(this));
    info = new AudioInfo();
    bgPanel = new JPanel() {
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (info.hasArtwork() || !fDrawn) {
          Debugger.warn("Found a new artwork!@MINIPLAYER");
          Graphics2D g2 = (Graphics2D) g;
          g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Float
              .parseFloat(ResourceFolder.pm.get(ProgramResourceManager.KEY_MINI_PLAYER_DEFAULT_BG_ALPHA))));
          g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
          BufferedImage last = info.getArtwork();
          last = DeImage.resize(last, getWidth(), getHeight());
          g2.drawImage(
              DeImage.createGradient(MiniDeImage.blurHash(
                  last, 5,
                  5), 255, 0, Directional.LEFT),
              0, 0, null);
          g2.dispose();
          fDrawn = true;
        } else {
          Debugger.warn("Reusing last artwork!");
        }
      }

      @Override
      public boolean isOptimizedDrawingEnabled() {
        return false;
      }
    };
    bgPanel.setPreferredSize(getPreferredSize());
    bgPanel.setOpaque(false);
    bgPanel.setDoubleBuffered(true);

    topPanel = new JPanel();
    topPanel.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height - 15));
    topPanel.setLayout(new GridLayout(1, 2));
    topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    topPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
    topPanel.setOpaque(false);

    mainLabel = new JLabel();
    mainLabel.setText(getLabelString());

    artLabel = new JLabel(
        new ImageIcon(DeImage.createRoundedBorder(DeImage.resizeNoDistort(info.getArtwork(), 128, 128), 10, null)));
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
    progressBar.setBackground(new Color(0,0,0,50));
    progressBar.setForeground(ColorManager.MAIN_FG_THEME);
    new Thread(() -> {
      while (true) {
        if (Global.player.getStream().isPlaying()) {
          if (Global.player.getStream().getLength() > 0) {
            progressBar
                .setValue(
                    (int) (Global.player.getStream().getPosition() * progressBar.getMaximum() / Global.player.getStream().getLength()));
          } else {
            progressBar.setValue(0);
          }
        }
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
    progressPanel.add(progressBar);

    fgPanel.add(topPanel, BorderLayout.NORTH);
    fgPanel.add(progressPanel, BorderLayout.SOUTH);

    add(bgPanel);
    add(fgPanel);
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
    bgPanel.repaint(100);
    mainLabel.setText(getLabelString());
    artLabel.setIcon(
        new ImageIcon(DeImage.createRoundedBorder(DeImage.resizeNoDistort(info.getArtwork(), 128, 128), 10, null)));
    revalidate();
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
