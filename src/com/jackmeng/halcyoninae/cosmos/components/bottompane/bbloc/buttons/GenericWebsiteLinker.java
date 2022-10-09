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
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a BBloc button that opens
 * to a URL object
 *
 * @author Jack Meng
 * @since 3.0
 */
public final class GenericWebsiteLinker {
    private GenericWebsiteLinker() {
    }

    /**
     * The generic implementation constructor
     *
     * @param url
     * @param tooltip
     * @param icon
     * @return
     */
    public static BBlocButton getButton(String url, String tooltip, ImageIcon icon) {
        return new WebsitePage(tooltip, icon, url);
    }

    public static class WebsitePage extends JButton implements BBlocButton {
        private final String url;

        /**
         * Creates a WebSite button
         *
         * @param tooltip The tooltip for the button
         * @param ico     The icon of the button
         * @param url     The url to link to.
         */
        public WebsitePage(String tooltip, ImageIcon ico, String url) {
            super(DeImage.resizeImage(ico, BBlocButton.DEFAULT_SIZE_BBLOC, BBlocButton.DEFAULT_SIZE_BBLOC));
            setToolTipText(tooltip);
            addActionListener(this);
            setOpaque(true);
            setBackground(null);
            setBorder(null);
            setDoubleBuffered(true);
            setContentAreaFilled(false);
            this.url = url;
        }

        @Override
        public JComponent getComponent() {
            return this;
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            try {
                Desktop.getDesktop().browse(new java.net.URI(url));
            } catch (Exception ex) {
                Debugger.log(ex);
            }
        }

    }
}
