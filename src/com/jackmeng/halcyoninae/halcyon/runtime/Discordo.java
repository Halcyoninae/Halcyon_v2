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

package com.jackmeng.halcyoninae.halcyon.runtime;

import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.halcyoninae.halcyon.utils.CLIStyles;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.TConstr;
import com.jackmeng.halcyoninae.halcyon.utils.TextParser;
import com.jackmeng.halcyoninae.tailwind.AudioInfo;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

/**
 * Represents the Discord Rich Presence Interfacing.
 * <p>
 * This class was originally found in 2.0 but I later
 * decided to change this and it is what it is now.
 * <p>
 * It should not be called by any external processes, and
 * should remain independent. Due to this, this class is completely
 * "lonely" and must rely completely on listeners and process calls
 * to function and/or update it's own state.
 *
 * @author Jack Meng
 * @since 2.0
 */
public class Discordo implements InfoViewUpdateListener {

    protected final String PROJECT_ID = "989355331761086475";
    private final String STATE = "Halcyon\n ";
    protected DiscordRichPresence rpc;

    /**
     * Starts the dispatch of the RPC
     */
    public void start() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
            .setReadyEventHandler(
                user -> Debugger.alert(new TConstr(new CLIStyles[]{CLIStyles.BOLD, CLIStyles.MAGENTA_BG},
                    "Launching Discord for user: " + user.username + "#" + user.discriminator + " | ID: "
                        + user.userId)))
            .build();
        DiscordRPC.discordInitialize(PROJECT_ID, handlers, true);

        String NOTHING_MUSIC = "Nothing";
        rpc = new DiscordRichPresence.Builder(STATE
            + NOTHING_MUSIC)
            .setBigImage("disk", STATE)
            .build();
        DiscordRPC.discordUpdatePresence(rpc);
    }

    /**
     * @param title A title to dispatch as
     */
    public void set(String title) {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
            .setReadyEventHandler(
                user -> Debugger.alert(new TConstr(new CLIStyles[]{CLIStyles.BOLD, CLIStyles.MAGENTA_BG},
                    "Launching Discord for user: " + user.username + "#" + user.discriminator + " | ID: "
                        + user.userId)))
            .build();
        DiscordRPC.discordInitialize(PROJECT_ID, handlers, true);
        rpc = new DiscordRichPresence.Builder(TextParser.parseAsPure(title))
            .setBigImage("disk", STATE)
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
