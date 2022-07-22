package com.halcyoninae.cosmos.components.bbloc.buttons;

import com.halcyoninae.cosmos.components.bbloc.BBlocButton;
import com.halcyoninae.cosmos.components.minimizeplayer.MiniPlayer;
import com.halcyoninae.cosmos.components.minimizeplayer.MiniPlayerListener;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MinimizePlayer extends JButton implements BBlocButton {
  private boolean pressed = false;
  private MiniPlayer player;

  public MinimizePlayer() {
    setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BBLOC_MINIMIZED_PLAYER), 16, 16));
    setToolTipText("Launch the audio player in a mini display form factor");
    setRolloverEnabled(false);
    setBorder(null);
    setOpaque(true);
    setDoubleBuffered(true);
    setBackground(null);
    addActionListener(this);
    player = new MiniPlayer();
    player.setMiniPlayerListener(new MiniPlayerListener() {
      @Override
      public void closingWindow() {
        pressed = false;
      }
    });
  }

  /**
   * @param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (!pressed) {
      player.run();
      pressed = true;
    }
  }

  /**
   * @return JComponent
   */
  @Override
  public JComponent getComponent() {
    return this;
  }

}
