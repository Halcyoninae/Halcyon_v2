package com.jackmeng.app.connections.discord;

import com.jackmeng.app.utils.TextParser;
import com.jackmeng.debug.Debugger;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

/**
 * Handles the Discord RPC instance and events.
 * 
 * @author Jack Meng
 * @since 2.1
 */
public class DiscordRPCHandler {
  private DiscordRichPresence presence;
  public static final String NOTHING_MUSIC = "Nothing.mp3";
  private static final String STATE = "Listening to\n ";

  /**
   * Sets the presence of the Discord client to param m.
   * 
   * @param m The String status
   */
  public synchronized void setCurrState(String m) {
    DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
        .setReadyEventHandler(
            user -> Debugger.log("Connected to Discord user: " + user.username + "#" + user.discriminator))
        .build();
    Debugger.log(m);
    DiscordRPC.discordInitialize(DiscordManager.APP_ID, handlers, true);

    presence = new DiscordRichPresence.Builder(STATE + m)
        .setBigImage("app-logo_512", "Experimental").setStartTimestamps(System.currentTimeMillis())
        .setSmallImage("app-logo_512", m)
        .build();
    DiscordRPC.discordUpdatePresence(presence);
  }

  /**
   * Starts the Discord RPC event.
   */
  public void start() {
    DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
        .setReadyEventHandler(
            user -> Debugger.log("Connected to Discord user: " + user.username + "#" + user.discriminator))
        .build();
    DiscordRPC.discordInitialize(DiscordManager.APP_ID, handlers, true);

    presence = new DiscordRichPresence.Builder(STATE
        + NOTHING_MUSIC)
        .setBigImage("app-logo_512", "Halcyon").setStartTimestamps(System.currentTimeMillis())
        .setSmallImage("app-logo_512", NOTHING_MUSIC)
        .build();
    DiscordRPC.discordUpdatePresence(presence);
  }
}