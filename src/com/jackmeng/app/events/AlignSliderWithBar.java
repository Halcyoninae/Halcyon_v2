package com.jackmeng.app.events;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AlignSliderWithBar implements ChangeListener {
  private JSlider slider;
  private JProgressBar progressBar;

  public AlignSliderWithBar(JSlider slider, JProgressBar bar) {
    this.progressBar = bar;
    this.slider = slider;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    progressBar.setValue(slider.getValue());
  }
  
}
