package com.jackmeng.music.components.bbloc.buttons;

import javax.swing.*;

import com.jackmeng.music.Global;
import com.jackmeng.music.Manager;
import com.jackmeng.music.components.bbloc.BBlocButton;
import com.jackmeng.music.components.dialog.SelectApplicableFolders;
import com.jackmeng.music.components.dialog.SelectApplicableFolders.FolderSelectedListener;
import com.jackmeng.music.utils.FileParser;

import java.awt.event.*;
import java.io.File;

public class AddFolder extends JButton implements BBlocButton {
  public AddFolder() {
    super(Manager.ADDFOLDER_BUTTON_TEXT);
    addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SelectApplicableFolders s = new SelectApplicableFolders();
        s.setFolderSelectedListener(new FolderSelectedListener() {
          @Override
          public void folderSelected(String folder) {
            if (FileParser.isEmptyFolder(new File(folder))) {
              Global.f.pokeFolder(folder);
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
