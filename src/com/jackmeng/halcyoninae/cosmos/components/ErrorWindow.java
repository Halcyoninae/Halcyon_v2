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

package com.jackmeng.halcyoninae.cosmos.components;

import com.jackmeng.halcyoninae.halcyon.Halcyon;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents an error dialog,
 * which is used to display error messages.
 * <p>
 * It is basically a text dialog.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class ErrorWindow extends JFrame implements Runnable {

    static final String DIALOG_ERROR_WIN_TITLE = "Exception!";
    /// Error Window Config START
    final int DIALOG_ERROR_MIN_WIDTH           = 300;
    final int DIALOG_ERROR_MIN_HEIGHT          = 150;
    /// Error Window Config END

    public ErrorWindow(String content) {
        super(DIALOG_ERROR_WIN_TITLE);
        setIconImage(Halcyon.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        setPreferredSize(new Dimension(DIALOG_ERROR_MIN_WIDTH, DIALOG_ERROR_MIN_HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        JTextArea jt = new JTextArea(content);
        jt.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jt);
        scrollPane.setPreferredSize(new Dimension(DIALOG_ERROR_MIN_WIDTH, DIALOG_ERROR_MIN_HEIGHT));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scrollPane);
    }

    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> {
            this.pack();
            this.setAlwaysOnTop(true);
            this.setVisible(true);
        });
    }
}
