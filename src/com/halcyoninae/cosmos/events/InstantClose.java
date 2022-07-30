package com.halcyoninae.cosmos.events;

import com.halcyoninae.halcyon.Halcyon;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.runtime.Program;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class InstantClose implements WindowListener {


  /**
   * @param e
   */
  @Override
  public void windowOpened(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }


  /**
   * @param e
   */
  @Override
  public void windowClosing(WindowEvent e) {
    Halcyon.bgt.getFrame().dispose();
    if (Global.player.getStream().isOpen() || Global.player.getStream().isPlaying()) {
      Global.player.getStream().close();
    }
    Program.forceSaveUserConf();
    System.exit(0);
  }


  /**
   * @param e
   */
  @Override
  public void windowClosed(WindowEvent e) {
  }


  /**
   * @param e
   */
  @Override
  public void windowIconified(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }


  /**
   * @param e
   */
  @Override
  public void windowDeiconified(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }


  /**
   * @param e
   */
  @Override
  public void windowActivated(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }


  /**
   * @param e
   */
  @Override
  public void windowDeactivated(WindowEvent e) {
    // TO BE IMPLEMENTED OR IGNORED
  }

}
