/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.halcyon.app.components.dialog;

/**
 * Displays a window that calls for a confirmation
 * status.
 *
 * @author Jack Meng
 * @since 3.0
 */
import javax.swing.*;

import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;

import java.awt.*;
import java.awt.event.*;

public class ConfirmWindow extends JFrame implements Runnable, ActionListener {
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
     * @param status The status (true || false)
     */
    void onStatus(boolean status);
  }

  private JButton confirm;

  private transient ConfirmationListener[] listeners;

  public ConfirmWindow(String content, ConfirmationListener... listeners) {
    super(Manager.DIALOG_CONFIRM_WIN_TITLE);
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    this.listeners = listeners;
    setPreferredSize(new Dimension(Manager.DIALOG_CONFIRM_MIN_WIDTH, Manager.DIALOG_CONFIRM_MIN_HEIGHT));
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
        new Dimension(Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH, Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT));
    container.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    JPanel jp = new JPanel();
    jp.setLayout(new FlowLayout(FlowLayout.CENTER));
    jp.setPreferredSize(
        new Dimension(Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_WIDTH, Manager.DIALOG_CONFIRM_PROMPT_AREA_MIN_HEIGHT));
    jp.add(confirm);
    jp.add(cancel);

    setLayout(new BorderLayout());
    setResizable(false);
    add(container, BorderLayout.NORTH);
    add(jp, BorderLayout.SOUTH);

    getContentPane().add(container);
  }

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

  @Override
  public void actionPerformed(ActionEvent e) {
    dispatchConfirmationEvents(e.getSource().equals(confirm));
    dispose();
  }
}
