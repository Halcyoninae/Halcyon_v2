package com.jackmeng.cosmos.components.minimizeplayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jackmeng.cosmos.components.inheritable.TruncLabelUI;
import com.jackmeng.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.halcyon.constant.ColorManager;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.DeImage;
import com.jackmeng.tailwind.AudioInfo;

import java.awt.*;

/**
 * This class holds all of the components to the main
 * MiniPlayer frame.
 *
 * @see com.jackmeng.cosmos.components.minimizeplayer.MiniPlayer
 *
 * @author Jack Meng
 * @since 3.2
 */
public class MiniContentPane extends JPanel implements InfoViewUpdateListener {
  private JLabel artWorkLabel;
  private JLabel infoLabel;
  private JPanel infoPanel, bottomPanel;
  private JProgressBar bar;
  private TruncLabelUI ui;
  private transient AudioInfo info;

  public MiniContentPane() {
    setPreferredSize(new Dimension(MiniPlayerManager.MINI_PLAYER_MIN_WIDTH, MiniPlayerManager.MINI_PLAYER_MIN_HEIGHT));
    setBorder(BorderFactory.createCompoundBorder(new LineBorder(ColorManager.BORDER_THEME), new EmptyBorder(5,5,5,5)));
    artWorkLabel = new JLabel();
    info = Global.ifp.getInfo();
    artWorkLabel.setIcon(new ImageIcon(DeImage.createRoundedBorder(
        DeImage.resizeNoDistort(info.getArtwork(), 128, 128), 15, ColorManager.BORDER_THEME)));

    ui = new TruncLabelUI();

    infoLabel = new JLabel(info.getTag(AudioInfo.KEY_MEDIA_TITLE));
    infoLabel.setUI(ui);
    infoLabel.setFont(new Font("Arial", Font.BOLD, 20));

    bottomPanel = new JPanel();
    bottomPanel.setPreferredSize(new Dimension(getPreferredSize().width, MiniPlayerManager.MINI_PLAYER_BAR_HEIGHT));

    bar = new JProgressBar(0, 500);
    bar.setIndeterminate(true);
    bar.setPreferredSize(new Dimension(getPreferredSize().width, MiniPlayerManager.MINI_PLAYER_BAR_HEIGHT));

    bottomPanel.add(bar);

    infoPanel = new JPanel();
    infoPanel.setPreferredSize(new Dimension(getPreferredSize().width,
        (getPreferredSize().height - MiniPlayerManager.MINI_PLAYER_BAR_HEIGHT)));
    infoPanel.add(infoLabel);

    setLayout(new BorderLayout());
    add(infoPanel, BorderLayout.NORTH);
    add(bottomPanel, BorderLayout.SOUTH);
  }

  private void scheduleRedraw() {
    artWorkLabel.setIcon(new ImageIcon(DeImage.createRoundedBorder(
        DeImage.resizeNoDistort(info.getArtwork(), 128, 128), 15, ColorManager.BORDER_THEME)));
    infoLabel.setText(info.getTag(AudioInfo.KEY_MEDIA_TITLE));
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
