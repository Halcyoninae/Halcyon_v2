package com.halcyoninae.cosmos.components.bbloc.buttons;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.halcyoninae.cosmos.components.bbloc.BBlocButton;
import com.halcyoninae.cosmos.components.waveform.WaveFormPane;
import com.halcyoninae.halcyon.constant.Global;

public class WaveFormLinker extends JButton implements BBlocButton {
  private WaveFormPane pane;

  public WaveFormLinker() {
    setPreferredSize(new Dimension(16, 16));
    addActionListener(this);
    pane = new WaveFormPane();
    Global.player.getStream().setFrameBufferListener(Global.waveForm);
  }


  /**
   * @return JComponent
   */
  @Override
  public JComponent getComponent() {
    return this;
  }


  /**
   * @param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    SwingUtilities.invokeLater(pane::run);
  }

}
