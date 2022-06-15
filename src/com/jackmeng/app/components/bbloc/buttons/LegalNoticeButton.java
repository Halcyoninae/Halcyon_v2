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

package com.jackmeng.app.components.bbloc.buttons;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;

import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.app.components.dialog.ConfirmWindow;
import com.jackmeng.app.components.dialog.LegalNoticeDialog;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

/**
 * A button that when pressed launches
 * {@link com.jackmeng.app.components.dialog.LegalNoticeDialog} which displays
 * information regarding legal documentation for all libraries and this program.
 * 
 * @author Jack Meng
 * @since 3.1
 */
public class LegalNoticeButton extends JButton implements BBlocButton {

  public LegalNoticeButton() {
    setIcon(Global.rd.getFromAsImageIcon(Manager.LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL));
    addActionListener(this);
    setOpaque(true);
    setBackground(null);
    setBorder(null);
    setContentAreaFilled(false);
  }

  private String readLegal() {
    File f = Global.rd.getFromAsFile(Manager.LEGAL_NOTICE_DOCS);
    StringBuilder sb = new StringBuilder();
    try {
      java.util.Scanner s = new java.util.Scanner(f);
      while (s.hasNextLine()) {
        sb.append(s.nextLine());
        sb.append("\n");
      }
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  @Override
  public JComponent getComponent() {
    return this;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    new LegalNoticeDialog(readLegal(), (ConfirmWindow.ConfirmationListener[]) null).run();
  }

}
