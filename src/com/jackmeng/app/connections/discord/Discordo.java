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
