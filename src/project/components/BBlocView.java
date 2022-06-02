package project.components;

import javax.swing.*;

import project.Global;
import project.Manager;
import project.components.dialog.SelectApplicableFolders;
import project.components.dialog.SelectApplicableFolders.FolderSelectedListener;
import project.utils.FileParser;

import java.awt.event.*;
import java.io.File;
import java.awt.*;

public class BBlocView extends JPanel {
  private JButton openAddFolder;

  public BBlocView() {
    super();
    openAddFolder = new JButton("+");
    openAddFolder.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SelectApplicableFolders s = new SelectApplicableFolders();
        s.setFolderSelectedListener(new FolderSelectedListener() {
          /// TODO: Make only one instance of this class or this instance of the program
          @Override
          public void folderSelected(String folder) {
            if (FileParser.isEmptyFolder(new File(folder))) {
              Global.f.pokeFolder(FileParser.folderName(folder));
            } else {
              Global.f.pokeFolder(FileParser.folderName(
                  folder),
                  FileParser.parseFileNames(FileParser.parseOnlyAudioFiles(new File(folder), Manager.ALLOWED_FORMATS)));
            }
          }
        });
        s.run();
      }
    });
    setPreferredSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.B_MAX_WIDTH, Manager.B_MAX_HEIGHT));
    add(openAddFolder);
  }
}
