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

import com.halcyoninae.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.halcyoninae.cosmos.components.toppane.layout.buttoncontrol.TimeControlSubTP;
import com.halcyoninae.cosmos.inheritable.LikeButton;
import com.halcyoninae.halcyon.constant.ColorManager;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.debug.Debugger;
import com.halcyoninae.halcyon.utils.DeImage;
import com.halcyoninae.tailwind.AudioInfo;
import com.halcyoninae.tailwind.TailwindEvent.TailwindStatus;
import com.halcyoninae.tailwind.TailwindListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * This class represents the GUI component collection
 * class that maintains all of the buttons like:
 * play, forward, volume.
 * <p>
 * This component is located under the
 * {@link com.halcyoninae.cosmos.components.toppane.layout.InfoViewTP}
 * component.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class ButtonControlTP extends JPanel
        implements InfoViewUpdateListener, ActionListener, ChangeListener, TailwindListener.StatusUpdateListener {
    /// ButtonControl Config START
    final int PLAY_PAUSE_ICON_SIZE = 40;
    final int OTHER_BUTTONS_SIZE = 24;
    final String BUTTONCTRL_PLAY_PAUSE_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/play_button.png";
    final String BUTTONCTRL_PAUSE_PLAY_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/pause_button.png";
    final String BUTTONCTRL_FWD_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/forward_button.png";
    final String BUTTONCTRL_BWD_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/backward_button.png";
    final String BUTTONCTRL_LOOP_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/loop_button.png";
    final String BUTTONCTRL_SHUFFLE_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/shuffle_button.png";
    final String BUTTONCTRL_MUTED_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/mute_button.png";
    final String BUTTONCTRL_NOMUTED_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/nomute_button.png";
    final String BUTTONCTRL_LIKE_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/like_button.png";
    final String BUTTONCTRL_NOLIKE_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/nolike_button.png";
    final String BUTTONCTRL_RESTART_ICON = Manager.RSC_FOLDER_NAME + "/buttoncontrol/restart_button.png";
    final String BUTTONCONTROL_SHUFFLE_ICON_PRESSED = Manager.RSC_FOLDER_NAME
            + "/buttoncontrol/shuffle_button_pressed.png";
    final String BUTTONCONTROL_LOOP_ICON_PRESSED = Manager.RSC_FOLDER_NAME
            + "/buttoncontrol/loop_button_pressed.png";
    public static final int BUTTONCONTROL_MIN_WIDTH = Manager.MIN_WIDTH;
    public static final int BUTTONCONTROL_MIN_HEIGHT = Manager.MIN_HEIGHT / 4;
    final int BUTTONCONTROL_MAX_WIDTH = Manager.MAX_WIDTH;
    final int BUTTONCONTROL_MAX_HEIGHT = Manager.MAX_HEIGHT / 4;
    public static final int BUTTONCONTROL_BOTTOM_TOP_BUDGET = 12;
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
    private TimeControlSubTP tsp;
    private transient AudioInfo aif;
    private boolean hasPlayed = false, flip = false;
    /// ButtonControl Config END

    public ButtonControlTP() {
        super();
        aif = null;
        setPreferredSize(new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT));
        setMinimumSize(new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT));
        setOpaque(false);
        setLayout(new GridLayout(2, 1));

        buttons = new JPanel();
        buttons.setPreferredSize(
                new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));
        buttons.setMinimumSize(
                new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 10, getPreferredSize().height / 6));

        playButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_PLAY_PAUSE_ICON),
                        PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
        playButton.setBackground(null);
        playButton.setBorder(null);
        playButton.setToolTipText("Play/Pause");
        playButton.addActionListener(this);
        playButton.setContentAreaFilled(false);
        playButton.setRolloverEnabled(false);
        playButton.setBorderPainted(false);

        nextButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_FWD_ICON),
                        OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        nextButton.setBackground(null);
        nextButton.setBorder(null);
        nextButton.setContentAreaFilled(false);
        nextButton.setToolTipText("Next track");
        nextButton.setRolloverEnabled(false);
        nextButton.setBorderPainted(false);
        nextButton.addActionListener(this);

        previousButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_BWD_ICON),
                        OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        previousButton.setBackground(null);
        previousButton.setBorder(null);
        previousButton.setToolTipText("Previous track");
        previousButton.setContentAreaFilled(false);
        previousButton.setRolloverEnabled(false);
        previousButton.setBorderPainted(false);
        previousButton.addActionListener(this);

        restartButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_RESTART_ICON),
                        OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        restartButton.setBackground(null);
        restartButton.setBorder(null);
        restartButton.setContentAreaFilled(false);
        restartButton.setToolTipText("Restart");
        restartButton.setRolloverEnabled(false);
        restartButton.setBorderPainted(false);
        restartButton.addActionListener(this);

        loopButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_LOOP_ICON),
                        OTHER_BUTTONS_SIZE,
                        OTHER_BUTTONS_SIZE));
        loopButton.setBackground(null);
        loopButton.setBorder(null);
        loopButton.setContentAreaFilled(false);
        loopButton.setToolTipText("Loop");
        loopButton.setRolloverEnabled(false);
        loopButton.setBorderPainted(false);
        loopButton.addActionListener(this);

        shuffleButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_SHUFFLE_ICON),
                        OTHER_BUTTONS_SIZE,
                        OTHER_BUTTONS_SIZE));
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
        volumeSlider.setPreferredSize(new Dimension(BUTTONCONTROL_MIN_WIDTH / 4, 20));
        volumeSlider.setMinimumSize(volumeSlider.getPreferredSize());
        volumeSlider.addChangeListener(this);
        volumeSlider.setToolTipText(volumeSlider.getValue() + "%");

        likeButton = new LikeButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_NOLIKE_ICON),
                        OTHER_BUTTONS_SIZE,
                        OTHER_BUTTONS_SIZE),
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_LIKE_ICON),
                        OTHER_BUTTONS_SIZE,
                        OTHER_BUTTONS_SIZE));
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
                new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));
        sliders.setMinimumSize(
                new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));

        progressSlider = new JSlider(0, 100);
        progressSlider.setValue(0);
        progressSlider.setFocusable(false);
        progressSlider.setForeground(ColorManager.MAIN_FG_THEME);
        progressSlider.setBackground(ColorManager.MAIN_BG_THEME);
        progressSlider.setBorder(null);
        progressSlider.putClientProperty("Slider.thumbSize", new java.awt.Dimension(0, 0));
        progressSlider.putClientProperty("Slider.trackWidth", 15);
        progressSlider.putClientProperty("Slider.thumBorderWidth", 0);
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

        tsp = new TimeControlSubTP();

        sliders.add(Box.createVerticalStrut(BUTTONCONTROL_BOTTOM_TOP_BUDGET / 2));
        sliders.add(progressSlider);
        sliders.add(Box.createVerticalStrut(BUTTONCONTROL_BOTTOM_TOP_BUDGET / 2));
        sliders.add(tsp);
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
        tsp = new TimeControlSubTP();
        add(buttons);
        add(sliders);

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
        if (Global.player.getStream().isPlaying()) {
            Global.player.getStream().stop();
            Global.player.getStream().close();
        }
        if (hasPlayed) {
            hasPlayed = false;
        }
        progressSlider.setValue(0);
    }

    /**
     * @param isLooping
     */
    public void callLoopFeatures(boolean isLooping) {
        if (isLooping) {
            loopButton.setIcon(
                    DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCONTROL_LOOP_ICON_PRESSED),
                            OTHER_BUTTONS_SIZE,
                            OTHER_BUTTONS_SIZE));
        } else {
            loopButton.setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_LOOP_ICON),
                    OTHER_BUTTONS_SIZE,
                    OTHER_BUTTONS_SIZE));
        }
    }

    /**
     * @param isShuffling
     */
    public void callShuffleFeatures(boolean isShuffling) {
        if (isShuffling) {
            shuffleButton.setIcon(
                    DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCONTROL_SHUFFLE_ICON_PRESSED),
                            OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        } else {
            shuffleButton.setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_SHUFFLE_ICON),
                    OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        }
    }

    /**
     * @param isLoop
     */
    private void loopVShuffleDuel(boolean isLoop) {
        if (isLoop && Global.player.isShuffling()) {
            Global.player.setShuffling(false);
            Global.player.setLooping(true);
            callShuffleFeatures(Global.player.isShuffling());
            callLoopFeatures(Global.player.isLooping());
        } else if (!isLoop && Global.player.isLooping()) {
            Global.player.setLooping(false);
            Global.player.setShuffling(true);
            callLoopFeatures(Global.player.isLooping());
            callShuffleFeatures(Global.player.isShuffling());
        } else if (isLoop) {
            Global.player.setLooping(!Global.player.isLooping());
            callLoopFeatures(Global.player.isLooping());
        } else if (!isLoop) {
            Global.player.setShuffling(!Global.player.isShuffling());
            callShuffleFeatures(Global.player.isShuffling());
        }
        Debugger.warn("Status: " + Global.player.isLooping() + " " + Global.player.isShuffling());
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
            if (!flip) {
                playButton
                        .setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_PAUSE_PLAY_ICON),
                                PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
            } else {
                playButton
                        .setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_PLAY_PAUSE_ICON),
                                PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
            }
            flip = !flip;
        } else if (e.getSource().equals(restartButton)) {
            Global.player.getStream().reset();
            Global.player.play();
            assertVolume();
        } else if (e.getSource().equals(nextButton)) {
            Global.player.requestNextTrack();
        } else if (e.getSource().equals(previousButton)) {
            Global.player.requestPreviousTrack();
        } else if (e.getSource().equals(loopButton)) {
            loopVShuffleDuel(true);
        } else if (e.getSource().equals(shuffleButton)) {
            loopVShuffleDuel(false);
        }
        loopButton.repaint(200);
        shuffleButton.repaint(200);
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

    @Override
    public void statusUpdate(TailwindStatus status) {
        Debugger.warn("Got ButtonCtrl: " + status.name());
        if (status.equals(TailwindStatus.PLAYING)) {
            playButton.setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_PAUSE_PLAY_ICON),
                    PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
        } else if (status.equals(TailwindStatus.PAUSED)) {
            playButton.setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_PLAY_PAUSE_ICON),
                    PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
        } else if (status.equals(TailwindStatus.CLOSED) || status.equals(TailwindStatus.END)) {
            playButton.setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(BUTTONCTRL_PLAY_PAUSE_ICON),
                    PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
        }
    }
}
