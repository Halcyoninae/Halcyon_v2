package com.jackmeng.app.events;


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
  private JSlider slider;
  private JProgressBar progressBar;

  /**
   * Constructs the AlignSliderWithBar object instance.
   * @param slider The Slider
   * @param bar The progress bar
   */
  public AlignSliderWithBar(JSlider slider, JProgressBar bar) {
    this.progressBar = bar;
    this.slider = slider;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    progressBar.setValue(slider.getValue());
  }
  
}
