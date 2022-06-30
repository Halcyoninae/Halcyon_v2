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

package com.jackmeng.cosmos.components.dialog;

import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;

public class ErrorWindow extends JFrame implements Runnable {
  public ErrorWindow(String content) {
    super(Manager.DIALOG_ERROR_WIN_TITLE);
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    setPreferredSize(new Dimension(Manager.DIALOG_ERROR_MIN_WIDTH, Manager.DIALOG_ERROR_MIN_HEIGHT));
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);
    JTextArea jt = new JTextArea(content);
    jt.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(jt);
    scrollPane.setPreferredSize(new Dimension(Manager.DIALOG_ERROR_MIN_WIDTH, Manager.DIALOG_ERROR_MIN_HEIGHT));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    getContentPane().add(scrollPane);
  }

  @Override
  public void run() {
    this.pack();
    this.setAlwaysOnTop(true);
    this.setVisible(true);
  }
}
