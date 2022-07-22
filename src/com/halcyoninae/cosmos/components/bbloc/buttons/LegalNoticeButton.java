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

package com.halcyoninae.cosmos.components.bbloc.buttons;

import javax.swing.*;

import com.halcyoninae.cosmos.components.bbloc.BBlocButton;
import com.halcyoninae.cosmos.components.info.InformationDialog;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.utils.DeImage;

import java.awt.event.ActionEvent;

/**
 * A button that when pressed launches
 * {@link com.halcyoninae.cosmos.components.info.InformationDialog} which displays
 * information regarding legal documentation for all libraries and this program.
 *
 * @author Jack Meng
 * @since 3.1
 */
public class LegalNoticeButton extends JButton implements BBlocButton {

  public LegalNoticeButton() {
    setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL), 16, 16));
    setRolloverIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.LEGALNOTICEBBLOC_ICON_BUTTON_PRESSED), 16, 16));
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
