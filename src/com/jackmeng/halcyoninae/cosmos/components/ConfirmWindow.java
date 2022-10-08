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

import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConfirmWindow extends JFrame implements Runnable, ActionListener {

    static final String DIALOG_CONFIRM_WIN_TITLE    = "Confirmation!";
    /// Confirm Config START
    final int DIALOG_CONFIRM_MIN_WIDTH              = 300;
    final int DIALOG_CONFIRM_MIN_HEIGHT             = 200;
    final int DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH  = DIALOG_CONFIRM_MIN_WIDTH - 20;
    final int DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT = DIALOG_CONFIRM_MIN_HEIGHT / 5;
    /// Confirm Config END
    private final JButton confirm;
    private final transient ConfirmationListener[] listeners;

    public ConfirmWindow(String content, ConfirmationListener... listeners) {
        super(DIALOG_CONFIRM_WIN_TITLE);
        setIconImage(Global.ico.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
        this.listeners = listeners;
        setPreferredSize(new Dimension(DIALOG_CONFIRM_MIN_WIDTH, DIALOG_CONFIRM_MIN_HEIGHT));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        confirm = new JButton("Confirm");
        confirm.setAlignmentY(Component.CENTER_ALIGNMENT);
        confirm.addActionListener(this);

        JButton cancel = new JButton("Cancel");
        cancel.setAlignmentY(Component.CENTER_ALIGNMENT);
        cancel.addActionListener(this);

        JTextArea prompt = new JTextArea(content);
        prompt.setLineWrap(true);
        prompt.setWrapStyleWord(true);
        prompt.setAlignmentY(Component.CENTER_ALIGNMENT);
        prompt.setEditable(false);

        JScrollPane container = new JScrollPane(prompt);
        container.setPreferredSize(
            new Dimension(DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH, DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT));
        container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp.setPreferredSize(
            new Dimension(DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH, DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT));
        jp.add(confirm);
        jp.add(cancel);

        setLayout(new BorderLayout());
        setResizable(false);
        add(container, BorderLayout.NORTH);
        add(jp, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispatchConfirmationEvents(false);
            }
        });

        getContentPane().add(container);
    }

    /**
     * @param status
     */
    private void dispatchConfirmationEvents(boolean status) {
        for (ConfirmationListener listener : listeners) {
            listener.onStatus(status);
        }
    }

    @Override
    public void run() {
        pack();
        setAlwaysOnTop(true);
        setVisible(true);
    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        dispatchConfirmationEvents(e.getSource().equals(confirm));
        dispose();
    }


    /**
     * A listener that is called upon for any classes that
     * wish to listen in on anything about the update for this confirmation
     * daemon.
     *
     * @author Jack Meng
     * @since 3.0
     */
    public interface ConfirmationListener {
        /**
         * Called for a status update for this class.
         *
         * @param status The status (true || false)
         */
        void onStatus(boolean status);
    }
}
