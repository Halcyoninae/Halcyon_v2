package com.jackmeng.app.components.bbloc.buttons;

import javax.swing.JComponent;

import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.app.constant.Global;
import com.jackmeng.app.constant.Manager;
import com.jackmeng.app.utils.DeImage;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class RefreshFileView extends JButton implements BBlocButton {

  public RefreshFileView() {
    super(new ImageIcon(DeImage
        .resize(DeImage.imageIconToBI(Global.rd.getFromAsImageIcon(Manager.BBLOC_REFRESH_FILEVIEW_ICON)), 16, 16)));
    setToolTipText(Manager.REFRESH_BUTTON_TOOLTIP);
    setOpaque(true);
    setBackground(null);
    setBorder(null);

    addActionListener(this);
  }

  @Override
  public JComponent getComponent() {
    return this;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Global.f.revalidateFiles();
  }
}
