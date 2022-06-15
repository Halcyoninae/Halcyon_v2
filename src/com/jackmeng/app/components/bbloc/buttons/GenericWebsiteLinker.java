/*
 *  Copyright: (C) 2022 name of Jack Meng
 * Halcyon MP4J is music-playing software.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.jackmeng.app.components.bbloc.buttons;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Desktop;

import com.jackmeng.app.components.bbloc.BBlocButton;
import com.jackmeng.app.utils.DeImage;
import com.jackmeng.debug.Debugger;

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
      setContentAreaFilled(false);
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
