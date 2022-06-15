/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.connections.discord;

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