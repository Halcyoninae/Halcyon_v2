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

package com.jackmeng.halcyon.app.components.bbloc.buttons;

import com.jackmeng.halcyon.app.components.bbloc.BBlocButton;
import com.jackmeng.halcyon.debug.Debugger;
import com.jackmeng.halcyon.utils.DeImage;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a BBloc button that opens
 * to a URL object
 *
 * @author Jack Meng
 * @since 3.0
 */
public class GenericWebsiteLinker {
  private GenericWebsiteLinker() {
  }

  public static class WebsitePage extends JButton implements BBlocButton {
    private final String url;

    /**
     * Creates a WebSite button
     *
     * @param tooltip The tooltip for the button
     * @param ico     The icon of the button
     * @param url     The url to link to.
     */
    public WebsitePage(String tooltip, ImageIcon ico, String url) {
      super(DeImage.resizeImage(ico, 16, 16));
      setToolTipText(tooltip);
      addActionListener(this);
      setOpaque(true);
      setBackground(null);
      setBorder(null);
      setDoubleBuffered(true);
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

  /**
   * The generic implementation constructor
   *
   * @param url
   * @param tooltip
   * @param icon
   * @return
   */
  public static BBlocButton getButton(String url, String tooltip, ImageIcon icon) {
    return new WebsitePage(tooltip, icon, url);
  }
}
