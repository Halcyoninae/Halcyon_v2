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
import com.jackmeng.halcyoninae.cosmos.components.waveform.WaveFormPane;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * This is a primarily experimental BBloc button and is used to
 * open a waveform panel that displays a visual representation of
 * the audio being played through the audio device
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.waveform.WaveForm
 * @since 3.2
 */
public class WaveFormLinker extends JButton implements BBlocButton {
    private WaveFormPane pane;

    public WaveFormLinker() {
        setIcon(DeImage.resizeImage(Global.ico.getFromAsImageIcon(Manager.RSC_FOLDER_NAME + "/bbloc/dots.png"), 16, 16));
        setRolloverIcon(DeImage.resizeImage(
            Global.ico.getFromAsImageIcon(Manager.RSC_FOLDER_NAME + "/bbloc/dots_pressed.png"), 16, 16));
        setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(16, 16));
        addActionListener(this);
        setToolTipText("WaveForm display Currently Unavailable due to technical issues");
    }

    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(pane);
    }

}
