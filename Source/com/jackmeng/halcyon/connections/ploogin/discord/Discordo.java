package com.jackmeng.halcyon.connections.ploogin.discord;

import com.jackmeng.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.TextParser;
import com.jackmeng.tailwind.AudioInfo;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Discordo implements InfoViewUpdateListener {

  protected static DiscordRichPresence rpc;
  protected static final String PROJECT_ID = "989355331761086475";
  private static final String STATE = "Listening to\n ", NOTHING_MUSIC = "Nothing";

  public void start() {
    DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
        .setReadyEventHandler(
            user -> Debugger.good("Connected to Discord user: " + user.username + "#" + user.discriminator))
        .build();
    DiscordRPC.discordInitialize(PROJECT_ID, handlers, true);

    rpc = new DiscordRichPresence.Builder(STATE
        + NOTHING_MUSIC)
        .setBigImage("logo", "Halcyon")
        .build();
    DiscordRPC.discordUpdatePresence(rpc);
  }


  /**
   * @param title
   */
  public void set(String title) {
    DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
        .setReadyEventHandler(
            user -> Debugger.log("Connected to Discord user: " + user.username + "#" + user.discriminator))
        .build();
    DiscordRPC.discordInitialize(PROJECT_ID, handlers, true);
    String b = STATE + TextParser.parseAsPure(title);
    rpc = new DiscordRichPresence.Builder(b)
        .setBigImage("logo", "Halcyon")
        .build();
    DiscordRPC.discordUpdatePresence(rpc);
  }


  /**
   * @param info
   */
  @Override
  public void infoView(AudioInfo info) {
    set(info.getTag(AudioInfo.KEY_MEDIA_TITLE));
  }

}
