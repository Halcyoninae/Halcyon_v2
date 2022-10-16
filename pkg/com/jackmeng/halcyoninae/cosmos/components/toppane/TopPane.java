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

package com.jackmeng.halcyoninae.cosmos.components.toppane;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.filelist.FileList;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * The Halcyon Music Player has two main components for it's main component: A
 * top and a
 * bottom. This class represents the top pane, which contains information
 * regarding the current
 * track and any sub-controls to modify playback.
 * <p>
 * This pane is very plain and only serving to align the components together
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.toppane.layout.ButtonControlTP
 * @see com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP
 * @see com.jackmeng.halcyoninae.cosmos.components.bottompane.BottomPane
 * @since 3.0
 */
public class TopPane extends JPanel {

    /// TopPane Config START
    public static final int TOPPANE_MIN_WIDTH  = Manager.MIN_WIDTH;
    public static final int TOPPANE_MIN_HEIGHT = Manager.MIN_HEIGHT / 2;

    final int TOPPANE_MAX_WIDTH                = TOPPANE_MIN_WIDTH - FileList.FILEVIEW_MAX_WIDTH;
    final int TOPPANE_MAX_HEIGHT               = Manager.MAX_HEIGHT / 2;
    /// TopPane Config END

    /**
     * Two instances of an InfoView and a ButtonControl view
     * are used to construct the Top View object.
     *
     * @param ifp  The InfoView instance to attach with
     * @param bctp The ButtonControl instance to attach with
     */
    public TopPane(InfoViewTP ifp, ButtonControlTP bctp) {
        setPreferredSize(new Dimension(TOPPANE_MIN_WIDTH, TOPPANE_MIN_HEIGHT));
        setMinimumSize(new Dimension(TOPPANE_MIN_WIDTH, TOPPANE_MIN_HEIGHT));
        setLayout(new BorderLayout());
        add(ifp, BorderLayout.PAGE_START);
        add(bctp, BorderLayout.PAGE_END);
    }
}
