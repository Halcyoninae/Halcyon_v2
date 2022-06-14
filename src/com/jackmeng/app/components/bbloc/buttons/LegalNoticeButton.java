package com.jackmeng.app.components.bbloc.buttons;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.awt.*;

import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.app.components.dialog.ConfirmWindow;
import com.jackmeng.app.components.dialog.LegalNoticeDialog;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;

public class LegalNoticeButton extends JButton implements BBlocButton {

  public LegalNoticeButton() {
    setIcon(Global.rd.getFromAsImageIcon(Manager.LEGALNOTICEBBLOC_ICON_BUTTON_NORMAL));
    addActionListener(this);
    setOpaque(true);
    setBackground(null);
    setBorder(null);
  }

  private String readLegal() {
    File f = Global.rd.getFromAsFile(Manager.LEGAL_NOTICE_DOCS);
    StringBuilder sb = new StringBuilder();
    try {
      java.util.Scanner s = new java.util.Scanner(f);
      while (s.hasNextLine()) {
        sb.append(s.nextLine());
        sb.append("\n");
      }
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  @Override
  public JComponent getComponent() {
    return this;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    new LegalNoticeDialog(readLegal(), (ConfirmWindow.ConfirmationListener[]) null).run();
  }

}
