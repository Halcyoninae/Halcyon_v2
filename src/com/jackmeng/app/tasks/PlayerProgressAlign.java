package com.jackmeng.app.tasks;

import javax.swing.JSlider;

import com.jackmeng.audio.Player;
import com.jackmeng.debug.Debugger;

public class PlayerProgressAlign implements Runnable {
  private JSlider slider;
  private Player p;

  public PlayerProgressAlign(JSlider progressSlider, Player p) {
    this.p = p;
    this.slider = progressSlider;
  }

  @Override
  public void run() {

  }

}
