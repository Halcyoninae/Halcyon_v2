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
import com.jackmeng.halcyoninae.cosmos.components.info.InformationDialog;
import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * A button that when pressed launches
 * {@link com.jackmeng.halcyoninae.cosmos.components.info.InformationDialog} which displays
 * information regarding legal documentation for all libraries and this program.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class LegalNoticeButton extends JButton implements BBlocButton {

    /// LEGALNOTICEDIALOG Config START
    public static final int LEGALNOTICEDIALOG_MIN_WIDTH = 500;
    public static final int LEGALNOTICEDIALOG_MIN_HEIGHT = 550;
    public static final String LEGAL_NOTICE_DOCS = Manager.RSC_FOLDER_NAME + "/files/LICENSE.html";
    public static final String LEGAL_NOTICE_PROPERTIES_DOCS = Manager.RSC_FOLDER_NAME + "/files/PROPERTIES_FILE.html";
    final String LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL = Manager.RSC_FOLDER_NAME + "/bbloc/legals_normal.png";
    final String LEGALNOTICEBBLOC_ICON_BUTTON_PRESSED = Manager.RSC_FOLDER_NAME + "/bbloc/legals_pressed.png";
    final int LEGALNOTICEDIALOG_SCROLL_PANE_MIN_HEIGHT = LEGALNOTICEDIALOG_MIN_HEIGHT - 100;
    /// LEGALNOTICEDIALOG Config END

    public LegalNoticeButton() {
        setIcon(DeImage.resizeImage(Halcyon.ico.getFromAsImageIcon(LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL), 16, 16));
        setRolloverIcon(DeImage.resizeImage(Halcyon.ico.getFromAsImageIcon(LEGALNOTICEBBLOC_ICON_BUTTON_PRESSED), 16, 16));
        addActionListener(this);
        setOpaque(true);
        setBackground(null);
        setDoubleBuffered(true);
        setBorder(null);
        setContentAreaFilled(false);
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
        new InformationDialog().run();
    }

}
