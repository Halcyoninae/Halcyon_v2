package com.jackmeng.halcyon.app.events;

import com.jackmeng.halcyon.Halcyon;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.debug.Program;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class InstantClose implements WindowListener {

  @Override
  public void windowOpened(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }

  @Override
  public void windowClosing(WindowEvent e) {
    Halcyon.bgt.getFrame().dispose();
    if (Global.player.getStream().isOpen() || Global.player.getStream().isPlaying()) {
      Global.player.getStream().close();
    }
    Program.forcedSavePlaylists();
    Program.forcedSaveLikedTracks();
    System.exit(0);
  }

  @Override
  public void windowClosed(WindowEvent e) {
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
