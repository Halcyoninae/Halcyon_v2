package com.jackmeng.app.components.bbloc.buttons;

import javax.swing.*;

import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.app.components.dialog.ConfirmWindow;
import com.jackmeng.app.components.dialog.SelectApplicableFolders;
import com.jackmeng.app.components.dialog.ConfirmWindow.ConfirmationListener;
import com.jackmeng.app.components.dialog.SelectApplicableFolders.FolderSelectedListener;
import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.utils.FileParser;

import java.awt.event.*;
import java.io.File;

/**
 * A BBloc button that handles when a user selects
 * a folder from the
 * {@link com.jackmeng.app.components.dialog.SelectApplicableFolders} instance.
 * 
 * 
 * @author Jack Meng
 * @since 3.0
 * @see com.jackmeng.app.components.dialog.SelectApplicableFolders
 */
public class AddFolder extends JButton implements BBlocButton {
  public AddFolder() {
    super(Global.rd.getFromAsImageIcon(Manager.FILEVIEW_DEFAULT_FOLDER_ICON));
    setToolTipText(Manager.ADDFOLDER_BUTTON_TOOLTIP);
    setOpaque(true);
    setBackground(null);
    setBorder(null);
    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SelectApplicableFolders s = new SelectApplicableFolders();
        s.setFolderSelectedListener(new FolderSelectedListener() {
          @Override
          public void folderSelected(String folder) { 

            
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
