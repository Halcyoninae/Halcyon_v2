package com.jackmeng.music.components.bbloc.buttons;

import com.jackmeng.ProjectManager;
import com.jackmeng.debug.Debugger;
import com.jackmeng.music.Global;
import com.jackmeng.music.Manager;
import com.jackmeng.music.components.bbloc.BBlocButton;
import com.jackmeng.music.utils.DeImage;

import java.awt.event.*;
import java.awt.Desktop;
import javax.swing.*;

public class ProjectPage extends JButton implements BBlocButton {

  public ProjectPage() {
    super(DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.GITHUB_LOGO_LIGHT), 16, 16));
    setToolTipText(Manager.PROJECTPAGE_BUTTON_TOOLTIP);
    addActionListener(this);
    setOpaque(true);
    setBackground(null);
    setBorder(null);
  }

  @Override
  public JComponent getComponent() {
    return this;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      Desktop.getDesktop().browse(
          new java.net.URI(ProjectManager.PROJECT_GITHUB_PAGE));
    } catch (Exception ex) {
      Debugger.log(ex);
    }
  }

}
