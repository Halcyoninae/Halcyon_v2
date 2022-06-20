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

package com.jackmeng.halcyon.app.components.bbloc.buttons;

import javax.swing.*;

import com.jackmeng.halcyon.app.components.bbloc.BBlocButton;
import com.jackmeng.halcyon.app.components.dialog.ConfirmWindow;
import com.jackmeng.halcyon.app.components.dialog.SelectApplicableFolders;
import com.jackmeng.halcyon.app.components.dialog.ConfirmWindow.ConfirmationListener;
import com.jackmeng.halcyon.app.components.dialog.SelectApplicableFolders.FolderSelectedListener;
import com.jackmeng.halcyon.constant.Global;
import com.jackmeng.halcyon.constant.Manager;
import com.jackmeng.halcyon.utils.DeImage;
import com.jackmeng.halcyon.utils.FileParser;

import java.awt.event.*;
import java.io.File;

/**
 * A BBloc button that handles when a user selects
 * a folder from the
 * {@link com.jackmeng.halcyon.app.components.dialog.SelectApplicableFolders} instance.
 *
 *
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.halcyon.app.components.dialog.SelectApplicableFolders
 */
public class AddFolder extends JButton implements BBlocButton {
  public AddFolder() {
    super(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.ADDFOLDER_BUTTON_DEFAULT_ICON), 16, 16));
    setRolloverIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.ADDFOLDER_BUTTON_PRESSED_ICON), 16, 16));
    setToolTipText(Manager.ADDFOLDER_BUTTON_TOOLTIP);
    setOpaque(true);
    setBackground(null);
    setBorder(null);
    setDoubleBuffered(true);
    setContentAreaFilled(false);
    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SelectApplicableFolders s = new SelectApplicableFolders();
        s.setFolderSelectedListener(new FolderSelectedListener() {
          @Override
          public void folderSelected(String folder) {
            if (Global.bp.containsFolder(folder)) {
              new ConfirmWindow(
                  "This folder seems to already be present in the current playlist listing. Do you still want to add it?",
                  new ConfirmationListener() {
                    @Override
                    public void onStatus(boolean status) {
                      if (status) {
                        Global.bp.pokeNewFileListTab(folder);
                      }
                    }
                  }).run();
            } else if (FileParser.isEmptyFolder(new File(folder))
                || !FileParser.contains(new File(folder), Manager.ALLOWED_FORMATS)) {
              new ConfirmWindow(
                  "This folder seems to be empty or does not seem to contain any Audio Files. Would you like to add this folder?",
                  new ConfirmationListener() {
                    @Override
                    public void onStatus(boolean status) {
                      if (status) {
                        Global.bp.pokeNewFileListTab(folder);
                      }
                    }
                  }).run();
            } else {
              Global.bp.pokeNewFileListTab(folder);
            }
          }
        });
        s.run();
      }
    });
  }

  @Override
  public JComponent getComponent() {
    return this;
  }
}
