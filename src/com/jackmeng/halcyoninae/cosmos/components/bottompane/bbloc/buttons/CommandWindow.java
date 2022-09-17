package com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.buttons;

import com.jackmeng.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.jackmeng.halcyoninae.halcyon.command.CommandPrompt;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CommandWindow extends JButton implements BBlocButton {

  final String CMD_BBLOC = Manager.RSC_FOLDER_NAME + "/bbloc/cmd-bbloc.png";

  public CommandWindow() {
        setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(CMD_BBLOC), 16, 16));
        addActionListener(this);
        setOpaque(true);
        setBackground(null);
        setDoubleBuffered(true);
        setBorder(null);
        setContentAreaFilled(false);
        setToolTipText("Open a command window to run internal commands & code snippets");
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
    CommandPrompt.main();
  }

}
