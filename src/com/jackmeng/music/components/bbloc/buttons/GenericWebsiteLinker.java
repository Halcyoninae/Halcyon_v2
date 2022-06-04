package com.jackmeng.music.components.bbloc.buttons;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Desktop;

import com.jackmeng.debug.Debugger;
import com.jackmeng.music.components.bbloc.BBlocButton;
import com.jackmeng.music.utils.DeImage;

public class GenericWebsiteLinker {
  private GenericWebsiteLinker() {
  }

  public static class WebsitePage extends JButton implements BBlocButton {
    private String url;

    public WebsitePage(String tooltip, ImageIcon ico, String url) {
      super(DeImage.resizeImage(ico, 16, 16));
      setToolTipText(tooltip);
      addActionListener(this);
      setOpaque(true);
      setBackground(null);
      setBorder(null);
      this.url = url;
    }

    @Override
    public JComponent getComponent() {
      return this;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
      try {
        Desktop.getDesktop().browse(new java.net.URI(url));
      } catch (Exception ex) {
        Debugger.log(ex);
      }
    }

  }

  public static BBlocButton getButton(String url, String tooltip, ImageIcon icon) {
    return new WebsitePage(tooltip, icon, url);
  }
}
