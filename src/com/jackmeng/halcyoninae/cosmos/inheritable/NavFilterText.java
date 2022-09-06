/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly..
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

package com.jackmeng.halcyoninae.cosmos.inheritable;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position;
import java.awt.event.ActionEvent;

public class NavFilterText extends NavigationFilter {
  private int pxL;
  private Action enL;

  public NavFilterText(int pxL, JTextComponent component) {
    this.pxL = pxL;
    enL = component.getActionMap().get("delete-previous");
    component.getActionMap().put("delete-previous", new BackspaceAction());
    component.setCaretPosition(pxL);
  }

  @Override
  public void setDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
    fb.setDot(Math.max(dot, pxL), bias);
  }

  @Override
  public void moveDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
    fb.moveDot(Math.max(dot, pxL), bias);
  }

  public class BackspaceAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
      JTextComponent component = (JTextComponent) e.getSource();

      if (component.getCaretPosition() > pxL) {
        enL.actionPerformed(null);
      }
    }
  }
}