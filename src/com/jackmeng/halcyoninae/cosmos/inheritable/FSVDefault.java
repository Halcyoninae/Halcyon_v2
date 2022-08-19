/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.cosmos.inheritable;

import javax.swing.*;

import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;

/**
 * This class mainly provides the necessary inheritable attributes
 * to the SelectionApplicableFolders
 * {@link com.jackmeng.halcyoninae.cosmos.dialog.SelectApplicableFolders} dialog
 * <p>
 * It should not be run by itself as it only provides assets inherits instead
 * of actual component inheritance.
 *
 * @author Jack Meng
 * @see com.jackmeng.halcyoninae.cosmos.dialog.SelectApplicableFolders
 * @see javax.swing.JFrame
 * @since 3.0
 */
public class FSVDefault extends JFrame {

    /**
     * Constructs the inheritable FSVDefault object to be inherited by
     */
    public FSVDefault() {
        setIconImage(Global.rd.getFromAsImageIcon(Manager.PROGRAM_ICON_LOGO).getImage());
    }
}
