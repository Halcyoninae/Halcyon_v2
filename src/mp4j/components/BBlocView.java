package mp4j.components;

import javax.swing.*;

import mp4j.Global;
import mp4j.Manager;
import mp4j.components.dialog.SelectApplicableFolders;
import mp4j.components.dialog.SelectApplicableFolders.FolderSelectedListener;
import mp4j.utils.FileParser;

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
    setPreferredSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.B_MIN_WIDTH, Manager.B_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.B_MAX_WIDTH, Manager.B_MAX_HEIGHT));
    add(openAddFolder);
  }
}
