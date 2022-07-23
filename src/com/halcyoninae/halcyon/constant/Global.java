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

package com.halcyoninae.halcyon.constant;

import com.halcyoninae.cosmos.components.bottompane.BottomPane;
import com.halcyoninae.cosmos.components.bottompane.LikeList;
import com.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP;
import com.halcyoninae.cosmos.components.toppane.layout.InfoViewTP;
import com.halcyoninae.halcyon.connections.resource.ResourceDistributor;
import com.halcyoninae.tailwind.wrapper.Player;

/**
 * This class holds any public scoped Objects that may be used throughout
 * the program.
 *
 * This class eliminates different classes having to hot potato pass
 * difference object instances to each other.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class Global {

  private Global() {}

  public static BottomPane bp = new BottomPane();
  public static final ResourceDistributor rd = new ResourceDistributor();
  public static ButtonControlTP bctp = new ButtonControlTP();
  public static InfoViewTP ifp = new InfoViewTP();
  public static Player player = new Player();
  public static LikeList ll = new LikeList();
}
