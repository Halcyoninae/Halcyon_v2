package com.jackmeng.app.connections.discord;

import com.jackmeng.app.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.audio.AudioInfo;

/**
 * A listener class that is for Info View updates and updates the 
 * Discord (DLL) RPC with the new information.
 * 
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.app.components.toppane.layout.InfoViewTP.InfoViewUpdateListener
 */
public class Discordo implements InfoViewUpdateListener {
  private DiscordRPCHandler handler = new DiscordRPCHandler();
  {
    handler.start();
  }

  @Override
  public void infoView(AudioInfo info) {
    handler.setCurrState(info.getTag(AudioInfo.KEY_MEDIA_TITLE));
  }

}
