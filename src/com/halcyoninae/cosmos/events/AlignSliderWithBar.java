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

package com.halcyoninae.cosmos.events;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class aligns a ProgressSlider with a ProgressBar.
 *
 * @author Jack Meng
 * @since 3.0
 * @see javax.swing.JSlider
 * @see javax.swing.JProgressBar
 */
public class AlignSliderWithBar implements ChangeListener {
  private final JSlider slider;
  private final JProgressBar progressBar;

  /**
   * Constructs the AlignSliderWithBar object instance.
   * @param slider The Slider
   * @param bar The progress bar
   */
  public AlignSliderWithBar(JSlider slider, JProgressBar bar) {
    this.progressBar = bar;
    this.slider = slider;
  }


  /**
   * @param e
   */
  @Override
  public void stateChanged(ChangeEvent e) {
    progressBar.setValue(slider.getValue());
  }

}
