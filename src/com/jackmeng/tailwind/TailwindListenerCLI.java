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

package com.jackmeng.tailwind;

import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.tailwind.TailwindEvent.TailwindStatus;

public class TailwindListenerCLI
    implements TailwindListener.StatusUpdateListener, TailwindListener.GenericUpdateListener {

  /**
   * @param status
   */
  @Override
  public void statusUpdate(TailwindStatus status) {
    Debugger.warn("TailwindPLAYER> " + status);
  }


  /**
   * @param event
   */
  @Override
  public void genericUpdate(TailwindEvent event) {
    Debugger.warn("TailwindPLAYER> " + event.getCurrentAudioInfo().getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
  }

}
