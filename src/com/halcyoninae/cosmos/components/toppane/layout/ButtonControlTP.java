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

package com.halcyoninae.cosmos.components.toppane.layout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.halcyoninae.cosmos.components.inheritable.LikeButton;
import com.halcyoninae.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.DeImage;
import com.halcyoninae.tailwind.AudioInfo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * This class represents the GUI component collection
 * class that maintains all of the buttons like:
 * play, forward, volume.
 *
 * This component is located under the
 * {@link com.halcyoninae.cosmos.components.toppane.layout.InfoViewTP}
 * component.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class ButtonControlTP extends JPanel
    implements InfoViewUpdateListener, ActionListener, ChangeListener {
  private final JButton playButton;
  private final JButton nextButton;
  private final JButton previousButton;
  private final JButton loopButton;
  private final JButton shuffleButton;
  private final JButton restartButton;
  private final LikeButton likeButton;
  private final JSlider progressSlider;
  private final JSlider volumeSlider;
  private final JPanel sliders;
  private final JPanel buttons;
  private transient AudioInfo aif;
  private boolean hasPlayed = false;

  public ButtonControlTP() {
    super();
    aif = null;
    setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setMinimumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setOpaque(false);
    setLayout(new GridLayout(2, 1));

    buttons = new JPanel();
    buttons.setPreferredSize(
        new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
    buttons.setMinimumSize(
        new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
    buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 12, getPreferredSize().height / 6));

    playButton = new JButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_PLAY_PAUSE_ICON),
            40, 40));
    playButton.setBackground(null);
    playButton.setBorder(null);
    playButton.setToolTipText("Play/Pause");
    playButton.addActionListener(this);
    playButton.setContentAreaFilled(false);
    playButton.setRolloverEnabled(false);
    playButton.setBorderPainted(false);

    nextButton = new JButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_FWD_ICON), 24, 24));
    nextButton.setBackground(null);
    nextButton.setBorder(null);
    nextButton.setContentAreaFilled(false);
    nextButton.setToolTipText("Next track");
    nextButton.setRolloverEnabled(false);
    nextButton.setBorderPainted(false);
    nextButton.addActionListener(this);

    previousButton = new JButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_BWD_ICON), 24, 24));
    previousButton.setBackground(null);
    previousButton.setBorder(null);
    previousButton.setToolTipText("Previous track");
    previousButton.setContentAreaFilled(false);
    previousButton.setRolloverEnabled(false);
    previousButton.setBorderPainted(false);

    restartButton = new JButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_RESTART_ICON), 24, 24));
    restartButton.setBackground(null);
    restartButton.setBorder(null);
    restartButton.setContentAreaFilled(false);
    restartButton.setToolTipText("Restart");
    restartButton.setRolloverEnabled(false);
    restartButton.setBorderPainted(false);
    restartButton.addActionListener(this);

    loopButton = new JButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LOOP_ICON), 24,
            24));
    loopButton.setBackground(null);
    loopButton.setBorder(null);
    loopButton.setContentAreaFilled(false);
    loopButton.setToolTipText("Loop");
    loopButton.setRolloverEnabled(false);
    loopButton.setBorderPainted(false);
    loopButton.addActionListener(this);

    shuffleButton = new JButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_SHUFFLE_ICON), 24,
            24));
    shuffleButton.setBackground(null);
    shuffleButton.setBorder(null);
    shuffleButton.setContentAreaFilled(false);
    shuffleButton.setToolTipText("Shuffle");
    shuffleButton.setRolloverEnabled(false);
    shuffleButton.setBorderPainted(false);
    shuffleButton.addActionListener(this);

    volumeSlider = new JSlider(0, 100);
    volumeSlider.setForeground(ColorManager.MAIN_FG_THEME);
    volumeSlider.setBorder(null);
    volumeSlider.setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH / 4, 20));
    volumeSlider.setMinimumSize(volumeSlider.getPreferredSize());
    volumeSlider.addChangeListener(this);
    volumeSlider.setToolTipText(volumeSlider.getValue() + "%");

    likeButton = new LikeButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_NOLIKE_ICON), 24,
            24),
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LIKE_ICON), 24,
            24));
    likeButton.setBackground(null);
    likeButton.setBorder(null);
    likeButton.setEnabled(false);
    likeButton.setContentAreaFilled(false);

    buttons.add(volumeSlider);
    buttons.add(restartButton);
    buttons.add(shuffleButton);
    buttons.add(previousButton);
    buttons.add(playButton);
    buttons.add(nextButton);
    buttons.add(loopButton);
    buttons.add(likeButton);

    sliders = new JPanel();
    sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
    sliders.setPreferredSize(
        new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
    sliders.setMinimumSize(
        new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));

    progressSlider = new JSlider(0, 100);
    progressSlider.setValue(0);
    progressSlider.setFocusable(false);
    progressSlider.setForeground(ColorManager.MAIN_FG_THEME);
    progressSlider.setBackground(ColorManager.MAIN_BG_THEME);
    progressSlider.setBorder(null);
    progressSlider.putClientProperty("Slider.thumbSize", new java.awt.Dimension(0, 0));
    progressSlider.putClientProperty("Slider.trackWidth", 15);
    progressSlider.putClientProperty("Slider.thumBorderWidth", 0);
    progressSlider.repaint();
    progressSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
    progressSlider.addChangeListener(this);
    new Thread(() -> {
      while (true) {
        if (Global.player.getStream().isPlaying()) {
          if (Global.player.getStream().getLength() > 0) {
            progressSlider
                .setValue((int) (Global.player.getStream().getPosition() * progressSlider.getMaximum()
                    / Global.player.getStream().getLength()));
            progressSlider.setToolTipText(
                String.format("%d:%02d / %d:%02d",
                    (int) (Global.player.getStream().getPosition() / 60000),
                    (int) (Global.player.getStream().getPosition() % 60000) / 1000,
                    (int) (Global.player.getStream().getLength() / 60000),
                    (int) (Global.player.getStream().getLength() % 60000) / 1000));
          } else {
            progressSlider.setValue(0);
            progressSlider.setToolTipText("0:00 / 0:00");
          }
        }
        try {
          Thread.sleep(30);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

    // sliders.add(progressSlider);
    // sliders.add(Box.createVerticalStrut(Manager.BUTTONCONTROL_MIN_HEIGHT / 10));
    // sliders.add(progressBar);
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        progressSlider.setMaximum(getWidth() - 10);
        volumeSlider.setPreferredSize(new Dimension(getPreferredSize().width / 4, 20));
        buttons.setLayout(
            new FlowLayout(FlowLayout.CENTER, e.getComponent().getWidth() / 35, getPreferredSize().height / 6));
        buttons.revalidate();
        volumeSlider.revalidate();
      }
    });

    add(buttons);
    // add(sliders);
    add(progressSlider);
  }

  /**
   * Sets the volume to the current slider's volume
   * if the stream is reset.
   */
  private void assertVolume() {
    Global.player.setVolume(Global.player.convertVolume(volumeSlider.getValue()));
  }

  /**
   * @param info
   */
  @Override
  public void infoView(AudioInfo info) {
    boolean wasPlaying = Global.player.getStream().isPlaying();
    if (aif != null
        && !aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH).equals(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH))) {
      Global.player.setFile(aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
    }
    aif = info;
    if (!likeButton.isEnabled())
      likeButton.setEnabled(true);
    if (Global.ll.isLiked(aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH))) {
      likeButton.like();
    } else {
      likeButton.noLike();
    }
    if (hasPlayed) {
      hasPlayed = false;
    }
    progressSlider.setValue(0);
    if (wasPlaying) {
      Global.player.getStream().play();
    }
  }

  /**
   * @param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(playButton)) {
      if (aif != null) {
        if (!Global.player.getStream().isPlaying()) {
          if (!hasPlayed) {
            Global.player.setFile(aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
            Global.player.play();
            hasPlayed = true;
          } else {
            Global.player.getStream().resume();
          }
          assertVolume();
        } else {
          Global.player.getStream().pause();
        }
      }
    } else if (e.getSource().equals(loopButton)) {
      /*
       * if (Global.player.getStream().isOpen()) {
       * if (!Global.player.getStream().isLooping()) {
       * Global.player.getStream().loop(AbstractAudio.LOOP_ENDLESS);
       * loopButton.setIcon(
       * DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.
       * BUTTONCONTROL_LOOP_ICON_PRESSED), 24,
       * 24));
       * } else {
       * Global.player.getStream().loop(1);
       * DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LOOP_ICON
       * ), 24,
       * 24);
       * }
       * }
       */
    } else if (e.getSource().equals(shuffleButton) && aif != null) {
      /*
       * if (Global.player.isLooping()) {
       * Global.player.setLooping(false);
       * loopButton.setIcon(
       * DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LOOP_ICON
       * ), 24,
       * 24));
       * }
       * if (Global.player.isShuffling()) {
       * Global.player.setShuffling(false);
       * shuffleButton.setIcon(
       * DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.
       * BUTTONCTRL_SHUFFLE_ICON), 24,
       * 24));
       * } else {
       * Global.player.setShuffling(true);
       * shuffleButton.setIcon(
       * DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.
       * BUTTONCONTROL_SHUFFLE_ICON_PRESSED), 24,
       * 24));
       * }
       */
    } else if (e.getSource().equals(restartButton)) {
      Global.player.getStream().reset();
      Global.player.play();
      assertVolume();
    } else if (e.getSource().equals(nextButton)) {
      Global.player.requestNextTrack();
    }
    loopButton.repaint();
    shuffleButton.repaint();
  }

  /**
   * @param e
   */
  @Override
  public synchronized void stateChanged(ChangeEvent e) {
    if (e.getSource().equals(volumeSlider)) {
      new Thread(() -> {
        try {
          Global.player.setVolume(Global.player.convertVolume(volumeSlider.getValue()));
          volumeSlider.setToolTipText(volumeSlider.getValue() + "%");

        } catch (NullPointerException ex) {
          Debugger.log(ex);
        }
      }).start();
    }
  }
}
