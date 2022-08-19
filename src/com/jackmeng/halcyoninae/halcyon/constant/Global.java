/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.halcyon.constant;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane;
import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.LikeList;
import com.jackmeng.halcyoninae.cosmos.components.moreapps.MoreApps;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP;
import com.jackmeng.halcyoninae.cosmos.components.waveform.WaveForm;
import com.jackmeng.halcyoninae.halcyon.connections.resource.ResourceDistributor;
import com.jackmeng.halcyoninae.tailwind.wrapper.Player;

/**
 * This class holds any public scoped Objects that may be used throughout
 * the program.
 * <p>
 * This class eliminates different classes having to hot potato pass
 * difference object instances to each other.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class Global {

    public static final ResourceDistributor rd = new ResourceDistributor();
    public static BottomPane bp = new BottomPane();
    public static ButtonControlTP bctp = new ButtonControlTP();
    public static InfoViewTP ifp = new InfoViewTP();
    public static Player player = new Player();
    public static LikeList ll = new LikeList();
    public static WaveForm waveForm = null; // SHOULD NOT BE USED AT THE MOMENT, this feature currently needs a lot more
                                            // optimizations
    public static MoreApps moreApps = new MoreApps();

    static {
        player.getStream().addStatusUpdateListener(bctp);
    }

    private Global() {
    }
}
