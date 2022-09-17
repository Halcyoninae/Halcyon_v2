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

package com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.buttons;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.jackmeng.halcyoninae.cosmos.components.minimizeplayer.MiniPlayer;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This BBloc container button is used to launch a miniplayer.
 * This button also keeps track of whether the given component (miniplayer)
 * has been closed before allowing another instance to be run; a lock is used to
 * keep the user from randomly opening multiple instances of a miniplayer.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.minimizeplayer.MiniPlayer
 * @since 3.2
 */
public class MinimizePlayer extends JButton implements BBlocButton {
    private final MiniPlayer player;
    private boolean pressed = false;

    public MinimizePlayer() {
        setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BBLOC_MINIMIZED_PLAYER), 16, 16));
        setToolTipText("Launch the audio player in a mini display form factor");
        setRolloverEnabled(false);
        setBorder(null);
        setOpaque(true);
        setDoubleBuffered(true);
        setBackground(null);
        addActionListener(this);
        player = new MiniPlayer();
        player.setMiniPlayerListener(() -> pressed = false);
    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!pressed) {
            player.run();
            pressed = true;
        }
    }

    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

}
