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

package com.jackmeng.cosmos.components.info.layout;

import com.jackmeng.cosmos.components.info.InformationTab;
import com.jackmeng.halcyon.connections.properties.ResourceFolder;
import com.jackmeng.halcyon.constant.Manager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileFromSourceTab extends JScrollPane implements InformationTab {
  private JEditorPane text;
  private String tabName = "";

  public FileFromSourceTab(String tabName, String textTab) {
    setPreferredSize(new Dimension(Manager.LEGALNOTICEDIALOG_MIN_WIDTH, Manager.LEGALNOTICEDIALOG_MIN_HEIGHT));
    setFocusable(true);

    this.tabName = tabName;

    text = new JEditorPane();
    text.setEditable(false);
    text.setContentType("text/html");
    text.setText(textTab);
    text.setCaretPosition(0);

    getViewport().add(text);
  }

  public static String getContent(String file) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    } catch (Exception e) {
      ResourceFolder.dispatchLog(e);
    }
    return sb.toString();
  }

  @Override
  public String getName() {
    return tabName;
  }

  @Override
  public String getToolTip() {
    return "View Legal Information regarding this program.";
  }

  @Override
  public JComponent getComponent() {
    return this;
  }
}
