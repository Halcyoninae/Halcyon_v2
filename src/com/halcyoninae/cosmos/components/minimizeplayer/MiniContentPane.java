package com.halcyoninae.cosmos.components.minimizeplayer;

import com.halcyoninae.cloudspin.CloudSpin;
import com.halcyoninae.cosmos.components.inheritable.TruncLabelUI;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
  private JPanel bgPanel, fgPanel;
  private transient AudioInfo info;
  private boolean fDrawn = false;

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
          BufferedImage last = info.getArtwork();
          last = DeImage.resize(last, getWidth(), getHeight());
          g2.drawImage(
              DeImage.createGradient(MiniDeImage.blurHash(
                  last, 3,
                  3), 230, 50, Directional.LEFT),
              0, 0, null);
          g2.dispose();
          fDrawn = true;
        } else {
          Debugger.warn("Reusing last artwork!");
        }
      }
    };

    add(bgPanel);
  }

  private void scheduleRedraw() {
    bgPanel.repaint();
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
