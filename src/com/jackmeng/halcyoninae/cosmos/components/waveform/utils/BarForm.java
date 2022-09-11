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

package com.jackmeng.halcyoninae.cosmos.components.waveform.utils;

import javax.swing.*;

import com.jackmeng.halcyoninae.halcyon.debug.Debugger;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * @author Jack Meng
 * @since 3.4.1
 */
public class BarForm extends JPanel {
  private int[] bars;
  private int barsWidth, barsGap, arcH, arcW, xOffset, yPadding;
  private Color fg, bg;

  public static record BoxWaveConf(int barsXOffset, int barsYPad, int barsArcH, int barsArcW) {
    public boolean assertNonNegative() {
      return barsYPad >= 0 && barsArcH >= 0 && barsArcW >= 0;
    }
  }

  public static record ColorConf(Color fg, Color bg) {
  }

  public static final int START_CENTER = -1;

  public BarForm(int width, int height, int barsGap, int barsWidth, BoxWaveConf bwc, ColorConf colors) {
    super();
    assert height > 0 && barsGap > 0 && barsWidth > 0
        && (bwc != null ? bwc.assertNonNegative() : bwc == null) && colors != null;
    this.barsGap = barsGap;
    this.barsWidth = barsWidth;
    this.arcH = bwc.barsArcH;
    this.arcW = bwc.barsArcW;
    this.xOffset = bwc.barsXOffset > width? bwc.barsXOffset - width: bwc.barsXOffset;
    this.yPadding = bwc.barsYPad > height ? bwc.barsYPad - height : bwc.barsYPad;
    this.bg = colors.bg;
    this.fg = colors.fg == null ? Color.BLACK : colors.fg;
    setPreferredSize(new Dimension(width, height));
    if (bg == null)
      setOpaque(false);

    bars = new int[width - (width / (barsGap + barsWidth))];
    bars = Utils.fillArr(bars, () -> Utils.rng(0, height));
    make(bars, 10L);
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        make(bars, 50L);
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

    g2.setColor(fg);
    for (int i = xOffset, j = 0; i < getWidth() - xOffset && j < bars.length; j++, i += barsGap) {
      if (bars[j] <= 0) {
        bars[j] = yPadding;
      }
      g2.fillRoundRect(i, getHeight() / 2 - bars[j] / 2, barsWidth, bars[j],
          arcW, arcH);
    }
  }

  public void make(int[] bars, long schedule) {
    this.bars = bars;
    SwingUtilities.invokeLater(() -> repaint(schedule));
  }

  public void makeRNG() {
    this.bars = Utils.fillArr(bars, () -> Utils.rng(0, getHeight()));
    SwingUtilities.invokeLater(() -> repaint(30L));
  }

  public int[] getCurrentDrawable() {
    return bars;
  }

  public static void main(String[] args) throws Exception {
    JFrame f = new JFrame();
    f.setPreferredSize(new Dimension(300, 100));
    BarForm bf = new BarForm(300, 100, 4, 3, new BoxWaveConf(10, 5, 5, 5),
        new ColorConf(Color.ORANGE, Color.BLACK));
    f.getContentPane().add(bf);
    f.pack();
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.setVisible(true);
  }
}
