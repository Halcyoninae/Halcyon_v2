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

package com.jackmeng.app.components.toppane.layout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jackmeng.app.components.dialog.LoadingDialog;
import com.jackmeng.app.components.inheritable.LikeButton;
import com.jackmeng.app.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.app.events.AlignSliderWithBar;
import com.jackmeng.audio.AudioInfo;
import com.jackmeng.connections.properties.ResourceFolder;
import com.jackmeng.constant.ColorManager;
import com.jackmeng.constant.Global;
import com.jackmeng.constant.Manager;
import com.jackmeng.constant.ProgramResourceManager;
import com.jackmeng.debug.Debugger;
import com.jackmeng.simple.audio.AbstractAudio;
import com.jackmeng.simple.audio.AudioException;
import com.jackmeng.simple.audio.StreamedAudio;
import com.jackmeng.utils.Wrapper;
import com.jackmeng.utils.DeImage;

import java.awt.*;

import java.awt.event.*;
import java.util.concurrent.Callable;

/**
 * This class represents the GUI component collection
 * class that maintains all of the buttons like:
 * play, forward, volume.
 *
 * This component is located under the
 * {@link com.jackmeng.app.components.toppane.layout.InfoViewTP}
 * component.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class ButtonControlTP extends JPanel
    implements InfoViewUpdateListener, ActionListener, ChangeListener {
  private JButton playButton;
  private JButton nextButton;
  private JButton previousButton;
  private JButton loopButton;
  private JButton shuffleButton;
  private JButton restartButton;
  private LikeButton likeButton;
  private JSlider progressSlider, volumeSlider;
  private JProgressBar progressBar;
  private JPanel sliders, buttons;
  private transient AudioInfo aif;
  private boolean hasPlayed = false;

  public ButtonControlTP() {
    super();
    aif = null;
    setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setMaximumSize(new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT));
    setMinimumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
    setOpaque(false);
    setLayout(new GridLayout(2, 1));

    buttons = new JPanel();
    buttons.setPreferredSize(
        new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
    buttons.setMinimumSize(
        new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
    buttons.setMaximumSize(
        new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT / 2));
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
    volumeSlider.setMaximumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH / 2, 20));
    volumeSlider.addChangeListener(this);

    likeButton = new LikeButton(
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_NOLIKE_ICON), 24,
            24),
        DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LIKE_ICON), 24,
            24));
    likeButton.setBackground(null);
    likeButton.setBorder(null);
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
    sliders.setMaximumSize(
        new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT / 2));

    progressBar = new JProgressBar(0, 100);
    progressBar.setStringPainted(true);
    progressBar.setString("0:00 / 0:00");
    progressBar.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height / 4));
    if (ResourceFolder.pm.get(ProgramResourceManager.KEY_PROGRAM_FORCE_OPTIMIZATION).equals("false")) {
      progressBar.setIndeterminate(true);
    }

    progressBar.setForeground(ColorManager.MAIN_FG_THEME);
    progressBar.setBorder(null);
    progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

    progressSlider = new JSlider(0, 100);
    progressSlider.setValue(0);
    progressSlider.setFocusable(false);
    progressSlider.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
          if (progressSlider.getValue() == 0) {
            Global.player.getStream().setPosition(0);
          } else {
            Debugger.unsafeLog(
                "Seeking to: " + (int) (progressSlider.getValue() * Global.player.getStream().getLength() / 100)
                    + " 0:00 format:" + String.format("%02d:%02d",
                        (int) (progressSlider.getValue() * Global.player.getStream().getLength() / 100) / 60,
                        (int) (progressSlider.getValue() * Global.player.getStream().getLength() / 100) % 60));

            Global.player.getStream().setPosition(
                (int) (progressSlider.getValue() * Global.player.getStream().getLength() / 100));
            Global.player.play();
          }
        }
      }
    });
    progressSlider.setForeground(ColorManager.MAIN_FG_THEME);
    progressSlider.setBorder(null);
    progressSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
    progressSlider.addChangeListener(new AlignSliderWithBar(progressSlider, progressBar));
    progressSlider.addChangeListener(this);
    Wrapper.async(() -> {
      while (true) {
        if (Global.player.getStream().isPlaying()) {
          progressSlider
              .setValue((int) (Global.player.getStream().getPosition() * progressSlider.getMaximum()
                  / Global.player.getStream().getLength()));
          progressSlider.setToolTipText(
              String.format("%d:%02d / %d:%02d",
                  (int) (Global.player.getStream().getPosition() / 60000),
                  (int) (Global.player.getStream().getPosition() % 60000) / 1000,
                  (int) (Global.player.getStream().getLength() / 60000),
                  (int) (Global.player.getStream().getLength() % 60000) / 1000));

          progressBar.setString(
              String.format("%d:%02d / %d:%02d",
                  (int) (Global.player.getStream().getPosition() / 60000),
                  (int) (Global.player.getStream().getPosition() % 60000) / 1000,
                  (int) (Global.player.getStream().getLength() / 60000),
                  (int) (Global.player.getStream().getLength() % 60000) / 1000));
        }
        try {
          Thread.sleep(30);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    sliders.add(progressSlider);
    sliders.add(Box.createVerticalStrut(Manager.BUTTONCONTROL_MIN_HEIGHT / 10));
    sliders.add(progressBar);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        progressSlider.setMaximum(getWidth() - 10);
        progressBar.setMaximum(progressSlider.getMaximum());
      }
    });

    add(buttons);
    add(sliders);
  }

  private void assertVolume() {
    Global.player.getStream().setVolume(Global.player.convertVolume(volumeSlider.getValue()));
  }

  @Override
  public void infoView(AudioInfo info) {
    progressBar.setIndeterminate(false);
    if (aif != null
        && !aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH).equals(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH))) {
      new Thread(() -> Global.player.setFile(aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH))).start();
    }
    aif = info;
    progressSlider.setValue(0);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(playButton)) {
      if (aif != null) {
        if (!Global.player.getStream().isPlaying()) {
          if (!hasPlayed) {
            Global.player.setFile(aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH));
            try {
              Global.player.getStream().open();
            } catch (AudioException e1) {
              Debugger.log(e1);
            }
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
      if (Global.player.getStream().isOpen()) {
        if (!Global.player.getStream().isLooping()) {
          Global.player.getStream().loop(AbstractAudio.LOOP_ENDLESS);
          loopButton.setIcon(
              DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCONTROL_LOOP_ICON_PRESSED), 24,
                  24));
        } else {
          Global.player.getStream().loop(1);
          DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LOOP_ICON), 24,
              24);
        }
      }
    } else if (e.getSource().equals(shuffleButton) && aif != null) {
      if (Global.player.isLooping()) {
        Global.player.setLooping(false);
        loopButton.setIcon(
            DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LOOP_ICON), 24,
                24));
      }
      if (Global.player.isShuffling()) {
        Global.player.setShuffling(false);
        shuffleButton.setIcon(
            DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_SHUFFLE_ICON), 24,
                24));
      } else {
        Global.player.setShuffling(true);
        shuffleButton.setIcon(
            DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCONTROL_SHUFFLE_ICON_PRESSED), 24,
                24));
      }

    } else if (e.getSource().equals(restartButton)) {
      // TO BE IMPLEMENTED
    }
    loopButton.repaint();
    shuffleButton.repaint();
  }

  @Override
  public synchronized void stateChanged(ChangeEvent e) {
    if (e.getSource().equals(volumeSlider)) {
      new Thread(() -> {
        try {
          Global.player.getStream().setVolume(Global.player.convertVolume(volumeSlider.getValue()));
        } catch (NullPointerException ex) {
          Debugger.log(ex);
        }
      }).start();
    }
  }
}
