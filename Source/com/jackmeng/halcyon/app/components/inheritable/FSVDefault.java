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

package com.jackmeng.halcyon.app.components.inheritable;

import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;

import javax.swing.*;

/**
 * This class mainly provides the necessary inheritable attributes
 * to the SelectionApplicableFolders
 * {@link com.jackmeng.halcyon.app.components.dialog.SelectApplicableFolders} dialog
 *
 * It should not be run by itself as it only provides assets inherits instead
 * of actual component inheritance.
 *
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.halcyon.app.components.dialog.SelectApplicableFolders
 * @see javax.swing.JFrame
 */
public class FSVDefault extends JFrame {

  /**
   * Constructs the inheritable FSVDefault object to be inherited by
   */
  public FSVDefault() {
    setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
  }
}
