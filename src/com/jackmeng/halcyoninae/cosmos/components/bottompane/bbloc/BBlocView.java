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

package com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.FileList;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * BBloc short for Button Bloc
 * is a view to display a button palette.
 * <p>
 * These buttons can be used to address certain
 * things regarding the player.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class BBlocView extends JPanel {

    /// BBloc Config START
    /// B Bloc Size Config START ///
    final int B_MIN_WIDTH  = Manager.MIN_WIDTH - FileList.FILEVIEW_MIN_WIDTH - 40;
    final int B_MIN_HEIGHT = Manager.MIN_HEIGHT / 2;

    final int B_MAX_WIDTH  = B_MIN_WIDTH - FileList.FILEVIEW_MAX_WIDTH;
    final int B_MAX_HEIGHT = Manager.MAX_HEIGHT / 2;
    /// B Bloc Size Config END ///
    /// BBloc Config END

    public BBlocView() {
        super();
        setPreferredSize(new Dimension(B_MIN_WIDTH, B_MIN_HEIGHT));
        setMinimumSize(new Dimension(B_MIN_WIDTH, B_MIN_HEIGHT));
    }

    /**
     * Attempts to add an N amount of BBlocButton implementations
     * to the current BBloc container.
     *
     * @param buttons A Vararg of BBloc Buttons
     * @author Jack Meng
     * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton
     * @since 3.0
     */
    public void addBBlockButtons(BBlocButton... buttons) {
        if (buttons.length > 0) {
            for (BBlocButton b : buttons) {
                add(b.getComponent());
            }
        }
    }
}
