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

import javax.swing.JComponent;

import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;
import com.jackmeng.utils.DeImage;

import javax.swing.*;
import java.awt.event.*;

public class RefreshFileView extends JButton implements BBlocButton {

  public RefreshFileView() {
    super(new ImageIcon(DeImage
        .resize(DeImage.imageIconToBI(Global.rd.getFromAsImageIcon(Manager.BBLOC_REFRESH_FILEVIEW_ICON)), 16, 16)));
    setToolTipText(Manager.REFRESH_BUTTON_TOOLTIP);
    setOpaque(true);
    setBackground(null);
    setBorder(null);
    setContentAreaFilled(false);
    addActionListener(this);
  }

  @Override
  public JComponent getComponent() {
    return this;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Global.bp.mastRevalidate();
  }
}
