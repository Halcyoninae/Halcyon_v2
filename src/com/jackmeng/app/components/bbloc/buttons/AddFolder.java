package com.jackmeng.app.components.bbloc.buttons;

import javax.swing.*;

import com.jackmeng.app.Global;
import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.app.components.dialog.ConfirmWindow;
import com.jackmeng.app.components.dialog.SelectApplicableFolders;
import com.jackmeng.app.components.dialog.ConfirmWindow.ConfirmationListener;
import com.jackmeng.app.components.dialog.SelectApplicableFolders.FolderSelectedListener;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.utils.FileParser;

import java.awt.event.*;
import java.io.File;

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
            if (Global.f.containsAbsolute(folder)) {
              new ConfirmWindow(
                  "This folder seems to already be present in the current playlist listing. Do you still want to add it?",
                  new ConfirmationListener() {
                    @Override
                    public void onStatus(boolean status) {
                      if (status) {
                        Global.f.pokeFolder(folder);
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
                        Global.f.pokeFolder(folder);
                      }
                    }
                  }).run();
            } else {
              Global.f.pokeFolder(
                  folder,
                  FileParser.parseFileNames(FileParser.parseOnlyAudioFiles(new File(folder), Manager.ALLOWED_FORMATS)));
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
