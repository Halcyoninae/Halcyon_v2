package com.jackmeng.cosmos.components.bbloc.buttons;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jackmeng.cosmos.components.bbloc.BBlocButton;
import com.jackmeng.cosmos.components.minimizeplayer.MiniPlayer;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.utils.DeImage;

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
    player.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        pressed = false;
      }
    });
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (!pressed) {
      player.run();
      pressed = true;
    }
  }

  @Override
  public JComponent getComponent() {
    return this;
  }

}
