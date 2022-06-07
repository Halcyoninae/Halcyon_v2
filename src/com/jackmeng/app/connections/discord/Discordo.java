package com.jackmeng.app.connections.discord;

import com.jackmeng.app.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.audio.AudioInfo;

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
