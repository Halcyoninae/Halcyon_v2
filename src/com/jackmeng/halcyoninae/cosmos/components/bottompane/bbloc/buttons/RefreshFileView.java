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
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Dispatches a call for all of the tabs in the FileList
 * to be refreshed. This button is shown to the user
 * if an automatic refresh call could not make it in time for a
 * refresh.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class RefreshFileView extends JButton implements BBlocButton {

    public RefreshFileView() {
        super(new ImageIcon(DeImage
            .resize(DeImage.imageIconToBI(Global.ico.getFromAsImageIcon(Manager.BBLOC_REFRESH_FILEVIEW_ICON)), 16,
                16)));
        setRolloverIcon(new ImageIcon(DeImage
            .resize(DeImage
                    .imageIconToBI(Global.ico.getFromAsImageIcon(Manager.BBLOC_REFRESH_FILEVIEW_ICON_PRESSED)), 16,
                16)));
        setToolTipText(Manager.REFRESH_BUTTON_TOOLTIP);
        setOpaque(true);
        setBackground(null);
        setBorder(null);
        setDoubleBuffered(true);
        setContentAreaFilled(false);
        addActionListener(this);
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
        Global.bp.mastRevalidate();
    }
}
