package com.jackmeng.app.events;

import java.awt.event.*;

import com.jackmeng.constant.Global;

public class InstantClose implements WindowListener {

  @Override
  public void windowOpened(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }

  @Override
  public void windowClosing(WindowEvent e) {
    if(Global.player.getStream().isOpen() || Global.player.getStream().isPlaying()) {
      Global.player.getStream().close();
    }
  }

  @Override
  public void windowClosed(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }

  @Override
  public void windowIconified(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }

  @Override
  public void windowActivated(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }
  
}
