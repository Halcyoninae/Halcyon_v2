/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.halcyon.runtime.constant;

import java.util.Timer;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane;
import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.LikeList;
import com.jackmeng.halcyoninae.cosmos.components.moreapps.MoreApps;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP;
import com.jackmeng.halcyoninae.cosmos.util.IconHandler;
import com.jackmeng.halcyoninae.halcyon.runtime.Player;
import com.jackmeng.halcyoninae.halcyon.utils.ResourceDistributor;

/**
 * This class holds any public scoped Objects that may be used throughout
 * the program.
 * <p>
 * This class eliminates different classes having to hot potato pass
 * difference object instances to each other.
 *
 * Important Note: The ordering of how each variable is declared
 * depends on their importance. For example an IconHandler needs
 * direct priority before the initialization of a GUI Component
 * that calls for Icons during initiatialization
 *
 * @author Jack Meng
 * @since 3.0
 */
public final class Global {
    public static IconHandler ico        = new IconHandler(Manager.RSC_FOLDER_NAME);
    public static ResourceDistributor rd = new ResourceDistributor();
    public static BottomPane bp          = new BottomPane();
    public static ButtonControlTP bctp   = new ButtonControlTP();
    public static InfoViewTP ifp         = new InfoViewTP();
    public static Player player          = new Player();
    public static LikeList ll            = new LikeList();
    public static MoreApps moreApps      = new MoreApps();
    public static Timer scheduler        = new Timer("Global Scheduler");

    static {
        player.getStream().addStatusUpdateListener(bctp);
    }

    public static void ping() {
        // DEFAULT_NO_IMPL
    }

    private Global() {
    }
}
