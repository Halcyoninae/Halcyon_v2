/*
 * Halcyon ~ exoad
 *
 * A simplistic & robust audio library that
 * is created OPENLY and distributed in hopes
 * that it will be benefitial.
 * ============================================
 * Copyright (C) 2021 Jack Meng
 * ============================================
 * The VENDOR_LICENSE is defined as:
 * "Standard Halcyoninae Protective 1.0 (MODIFIED) License or
 * later"
 *
 * Subsiding Wrappers are defined as:
 * "GUI Wrappers, Audio API Wrappers provided within the base
 * build of the original software"
 * ============================================
 * The Halcyon Audio API & subsiding wrappers
 * are licensed under the provided VENDOR_LICENSE
 * You are permitted to redistribute and/or modify this
 * piece of software in the source or binary form under
 * the VENDOR_LICENSE. You are permitted to link this
 * software statically or dynamically under the descriptions
 * of VENDOR_LICENSE without classpath exception.
 *
 * THE SOFTWARE AND ALL SUBSETS ARE PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ============================================
 * If you did not receive a copy of the VENDOR_LICENSE,
 * consult the following link:
 * https://raw.githubusercontent.com/Halcyoninae/Halcyon/live/LICENSE.txt
 * ============================================
 */

package com.jackmeng.halcyoninae.cosmos.components;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position;
import java.awt.event.ActionEvent;

public class NavFilterText extends NavigationFilter {
  private final int pxL;
  private final Action enL;

  public NavFilterText(int pxL, JTextComponent component) {
    this.pxL = pxL;
    enL = component.getActionMap().get("delete-previous");
    component.getActionMap().put("delete-previous", new BackspaceAction());
    component.setCaretPosition(pxL);
  }


  /**
   * @param fb
   * @param dot
   * @param bias
   */
  @Override
  public void setDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
    fb.setDot(Math.max(dot, pxL), bias);
  }


  /**
   * @param fb
   * @param dot
   * @param bias
   */
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