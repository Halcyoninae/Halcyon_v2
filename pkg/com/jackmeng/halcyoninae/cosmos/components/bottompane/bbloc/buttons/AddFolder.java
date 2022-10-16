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

import com.jackmeng.halcyoninae.cosmos.components.ConfirmWindow;
import com.jackmeng.halcyoninae.cosmos.components.SelectApplicableFolders;
import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
import com.jackmeng.locale.FileParser;

import javax.swing.*;
import java.io.File;

/**
 * A BBloc button that handles when a user selects
 * a folder from the
 * {@link com.jackmeng.halcyoninae.cosmos.components.SelectApplicableFolders}
 * instance.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.components.SelectApplicableFolders
 * @since 3.0
 */
public class AddFolder extends JButton implements BBlocButton {
    public AddFolder() {
        super(DeImage.resizeImage(Global.ico.getFromAsImageIcon(Manager.ADDFOLDER_BUTTON_DEFAULT_ICON),
                DEFAULT_SIZE_BBLOC, DEFAULT_SIZE_BBLOC));
        setToolTipText(Manager.ADDFOLDER_BUTTON_TOOLTIP);
        setOpaque(true);
        setBackground(null);
        setBorder(null);
        setDoubleBuffered(true);
        setContentAreaFilled(false);
        addActionListener(e -> {
            SelectApplicableFolders s = new SelectApplicableFolders();
            s.setFolderSelectedListener(folder -> {
                if (FileParser.isEmptyFolder(new File(folder))
                    || !FileParser.contains(new File(folder), Manager.ALLOWED_FORMATS)) {
                    new ConfirmWindow(
                        "This folder seems to be empty or does not seem to contain any Audio Files. Would you like to add this folder?",
                        status -> {
                            if (status) {
                                Global.bp.pokeNewFileListTab(folder);
                            }
                        }).run();
                } else {
                    Global.bp.pokeNewFileListTab(folder);
                }
            });
            s.run();
        });
    }

    /**
     * @return JComponent
     */
    @Override
    public JComponent getComponent() {
        return this;
    }
}
