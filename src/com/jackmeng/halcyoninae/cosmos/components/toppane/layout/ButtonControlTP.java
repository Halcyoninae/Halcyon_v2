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

package com.jackmeng.halcyoninae.cosmos.components.toppane.layout;

import com.jackmeng.halcyoninae.cosmos.components.LikeButton;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP.InfoViewUpdateListener;
import com.jackmeng.halcyoninae.cosmos.components.toppane.layout.buttoncontrol.TimeControlSubTP;
import com.jackmeng.halcyoninae.cosmos.components.waveform.utils.BarForm;
import com.jackmeng.halcyoninae.cosmos.components.waveform.utils.BarForm.BoxWaveConf;
import com.jackmeng.halcyoninae.cosmos.components.waveform.utils.BarForm.ColorConf;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Global;
import com.jackmeng.halcyoninae.halcyon.runtime.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.utils.DeImage;
import com.jackmeng.halcyoninae.halcyon.utils.Debugger;
import com.jackmeng.halcyoninae.halcyon.utils.TimeParser;
import com.jackmeng.halcyoninae.tailwind.AudioInfo;
import com.jackmeng.halcyoninae.tailwind.TailwindEvent.TailwindStatus;
import com.jackmeng.halcyoninae.tailwind.TailwindListener;
import com.jackmeng.locale.Localized;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Localized(stability = false)

/**
 * This class represents the GUI component collection
 * class that maintains all of the buttons like:
 * play, forward, volume.
 * <p>
 * This component is located under the
 * {@link com.jackmeng.halcyoninae.cosmos.components.toppane.layout.InfoViewTP}
 * component.
 *
 * @author Jack Meng
 * @since 3.0
 */
public class ButtonControlTP extends JPanel
    implements InfoViewUpdateListener, ActionListener, ChangeListener, TailwindListener.StatusUpdateListener {
    public static final int BUTTONCONTROL_MIN_WIDTH         = Manager.MIN_WIDTH;
    public static final int BUTTONCONTROL_MIN_HEIGHT        = Manager.MIN_HEIGHT / 4;
    public static final int BUTTONCONTROL_BOTTOM_TOP_BUDGET = 12;
    /// ButtonControl Config START
    final int PLAY_PAUSE_ICON_SIZE                          = 40;
    final int OTHER_BUTTONS_SIZE                            = 24;
    final String BUTTONCTRL_PLAY_PAUSE_ICON                 = Manager.RSC_FOLDER_NAME + "/buttoncontrol/play_button.png";
    final String BUTTONCTRL_PAUSE_PLAY_ICON                 = Manager.RSC_FOLDER_NAME + "/buttoncontrol/pause_button.png";
    final String BUTTONCTRL_FWD_ICON                        = Manager.RSC_FOLDER_NAME + "/buttoncontrol/forward_button.png";
    final String BUTTONCTRL_BWD_ICON                        = Manager.RSC_FOLDER_NAME + "/buttoncontrol/backward_button.png";
    final String BUTTONCTRL_LOOP_ICON                       = Manager.RSC_FOLDER_NAME + "/buttoncontrol/loop_button.png";
    final String BUTTONCTRL_SHUFFLE_ICON                    = Manager.RSC_FOLDER_NAME + "/buttoncontrol/shuffle_button.png";
    final String BUTTONCTRL_MUTED_ICON                      = Manager.RSC_FOLDER_NAME + "/buttoncontrol/mute_button.png";
    final String BUTTONCTRL_NOMUTED_ICON                    = Manager.RSC_FOLDER_NAME + "/buttoncontrol/nomute_button.png";
    final String BUTTONCTRL_LIKE_ICON                       = Manager.RSC_FOLDER_NAME + "/buttoncontrol/like_button.png";
    final String BUTTONCTRL_NOLIKE_ICON                     = Manager.RSC_FOLDER_NAME + "/buttoncontrol/nolike_button.png";
    final String BUTTONCTRL_RESTART_ICON                    = Manager.RSC_FOLDER_NAME + "/buttoncontrol/restart_button.png";
    final String BUTTONCTRL_INFORMATION_ICON                = Manager.RSC_FOLDER_NAME + "/buttoncontrol/information_button.png";
    final String BUTTONCONTROL_SHUFFLE_ICON_PRESSED         = Manager.RSC_FOLDER_NAME
        + "/buttoncontrol/shuffle_button_pressed.png";
    final String BUTTONCONTROL_LOOP_ICON_PRESSED            = Manager.RSC_FOLDER_NAME
        + "/buttoncontrol/loop_button_pressed.png";
    final int BUTTONCONTROL_MAX_WIDTH                       = Manager.MAX_WIDTH;
    final int BUTTONCONTROL_MAX_HEIGHT                      = Manager.MAX_HEIGHT / 4;
    private final JButton playButton;
    private final JButton nextButton;
    private final JButton previousButton;
    private final JButton loopButton;
    private final JButton shuffleButton;
    private final JButton informationButton;
    private final LikeButton likeButton;
    private final JSlider progressSlider;
    private BarForm bf;
    private final JSlider volumeSlider;
    private final TimeControlSubTP tsp;
    private final JPanel buttons;
    private final transient ExecutorService timeKeeper;
    private transient AudioInfo aif = new AudioInfo();
    private boolean hasPlayed = false;
    /// ButtonControl Config END

    public ButtonControlTP() {
        super();
        setPreferredSize(new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT));
        setMinimumSize(new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT));
        setOpaque(false);
        setLayout(new GridLayout(2, 1));

        buttons = new JPanel();
        buttons.setPreferredSize(
            new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));
        buttons.setMinimumSize(
            new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 10, getPreferredSize().height / 1));

        playButton = new JButton(
            DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_PLAY_PAUSE_ICON),
                PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
        playButton.setBackground(null);
        playButton.setBorder(null);
        playButton.setToolTipText("Play/Pause");
        playButton.addActionListener(this);
        playButton.setContentAreaFilled(false);
        playButton.setRolloverEnabled(false);
        playButton.setBorderPainted(false);

        informationButton = new JButton(DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_INFORMATION_ICON),
            OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        informationButton.setBackground(null);
        informationButton.setBorder(null);
        informationButton.setContentAreaFilled(false);
        informationButton.setToolTipText("View the current track's information");
        informationButton.setRolloverEnabled(false);
        informationButton.setBorderPainted(false);
        informationButton.addActionListener(this);

        nextButton = new JButton(
            DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_FWD_ICON),
                OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        nextButton.setBackground(null);
        nextButton.setBorder(null);
        nextButton.setContentAreaFilled(false);
        nextButton.setToolTipText("Next track");
        nextButton.setRolloverEnabled(false);
        nextButton.setBorderPainted(false);
        nextButton.addActionListener(this);

        previousButton = new JButton(
            DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_BWD_ICON),
                OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        previousButton.setBackground(null);
        previousButton.setBorder(null);
        previousButton.setToolTipText("Previous track");
        previousButton.setContentAreaFilled(false);
        previousButton.setRolloverEnabled(false);
        previousButton.setBorderPainted(false);
        previousButton.addActionListener(this);

        loopButton = new JButton(
            DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_LOOP_ICON),
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
            DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_SHUFFLE_ICON),
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
            DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_NOLIKE_ICON),
                OTHER_BUTTONS_SIZE,
                OTHER_BUTTONS_SIZE),
            DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_LIKE_ICON),
                OTHER_BUTTONS_SIZE,
                OTHER_BUTTONS_SIZE));
        likeButton.setBackground(null);
        likeButton.setBorder(null);
        likeButton.setEnabled(false);
        likeButton.setContentAreaFilled(false);

        buttons.add(volumeSlider);
        buttons.add(informationButton);
        buttons.add(shuffleButton);
        buttons.add(previousButton);
        buttons.add(playButton);
        buttons.add(nextButton);
        buttons.add(loopButton);
        buttons.add(likeButton);

        JPanel overlayWaveForm = new JPanel();
        overlayWaveForm.setLayout(new OverlayLayout(overlayWaveForm));

        JPanel sliders = new JPanel();
        sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
        sliders.setPreferredSize(
            new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));
        sliders.setMinimumSize(
            new Dimension(BUTTONCONTROL_MIN_WIDTH, BUTTONCONTROL_MIN_HEIGHT / 2));

        progressSlider = new JSlider(0, 100);
        progressSlider.setValue(0);
        progressSlider.setFocusable(false);
        progressSlider.setForeground(ColorManager.MAIN_FG_THEME);
        progressSlider.setBorder(null);
        progressSlider.setOpaque(false);
        progressSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressSlider.addChangeListener(this);

        bf = new BarForm(Toolkit.getDefaultToolkit().getScreenSize().width, 10, 1, 3, new BoxWaveConf(10, 5, 2, 3), new ColorConf(ColorManager.BORDER_THEME, null));

        JPanel bfWrapper = new JPanel();
        bfWrapper.setLayout(new BorderLayout());
        bfWrapper.setOpaque(false);
        bfWrapper.add(bf, BorderLayout.CENTER);

        overlayWaveForm.add(progressSlider);
        overlayWaveForm.add(bfWrapper);

        tsp = new TimeControlSubTP();

        timeKeeper = Executors.newSingleThreadExecutor();
        timeKeeper.submit(() -> {
            while (true) {
                if (Global.player.getStream().isPlaying()) {
                    if (Global.player.getStream().getLength() > 0) {
                        progressSlider
                            .setValue((int) (Global.player.getStream().getPosition() * progressSlider.getMaximum()
                                / Global.player.getStream().getLength()));

                        tsp.setTimeText(TimeParser.fromMillis(Global.player.getStream().getPosition()));

                    } else {
                        progressSlider.setValue(0);
                        tsp.setTimeText("00:00:00");
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        sliders.add(Box.createVerticalStrut(BUTTONCONTROL_BOTTOM_TOP_BUDGET / 5));
        sliders.add(overlayWaveForm);
        sliders.add(Box.createVerticalStrut(BUTTONCONTROL_BOTTOM_TOP_BUDGET / 5));
        sliders.add(tsp);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                progressSlider.setMaximum(getWidth() - 10);
                volumeSlider.setPreferredSize(new Dimension(getPreferredSize().width / 4, 20));
                buttons.setLayout(
                    new FlowLayout(FlowLayout.CENTER, e.getComponent().getWidth() / 35,
                        getPreferredSize().height / 6));
                buttons.revalidate();
                volumeSlider.revalidate();
            }
        });
        add(buttons);
        add(sliders);
        bf.makeRNG();
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
    @Localized
    @Override
    public void infoView(AudioInfo info) {
        if (aif != null
            && !aif.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH)
            .equals(info.getTag(AudioInfo.KEY_ABSOLUTE_FILE_PATH))) {
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
        tsp.setTimeText("00:00:00");
        bf.makeRNG();
    }

    /**
     * @param isLooping
     */
    @Localized
    public void callLoopFeatures(boolean isLooping) {
        if (isLooping) {
            loopButton.setIcon(
                DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCONTROL_LOOP_ICON_PRESSED),
                    OTHER_BUTTONS_SIZE,
                    OTHER_BUTTONS_SIZE));
        } else {
            loopButton.setIcon(DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_LOOP_ICON),
                OTHER_BUTTONS_SIZE,
                OTHER_BUTTONS_SIZE));
        }
    }

    /**
     * @param isShuffling
     */
    @Localized
    public void callShuffleFeatures(boolean isShuffling) {
        if (isShuffling) {
            shuffleButton.setIcon(
                DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCONTROL_SHUFFLE_ICON_PRESSED),
                    OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        } else {
            shuffleButton.setIcon(DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_SHUFFLE_ICON),
                OTHER_BUTTONS_SIZE, OTHER_BUTTONS_SIZE));
        }
    }

    /**
     * @param isLoop
     */
    @Localized
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
            /*
             * if (!flip) {
             * playButton
             * .setIcon(DeImage.resizeImage(Global.ico.getFromAsImageIcon(
             * BUTTONCTRL_PAUSE_PLAY_ICON),
             * PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
             * } else {
             * playButton
             * .setIcon(DeImage.resizeImage(Global.ico.getFromAsImageIcon(
             * BUTTONCTRL_PLAY_PAUSE_ICON),
             * PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
             * }
             * flip = !flip;
             */
        } else if (e.getSource().equals(nextButton)) {
            Global.player.requestNextTrack();
        } else if (e.getSource().equals(previousButton)) {
            Global.player.requestPreviousTrack();
        } else if (e.getSource().equals(loopButton)) {
            loopVShuffleDuel(true);
        } else if (e.getSource().equals(shuffleButton)) {
            loopVShuffleDuel(false);
        } else if (e.getSource().equals(informationButton)) {
            SwingUtilities.invokeLater(() -> aif.launchAsDialog().run());
        }

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


    /**
     * @param status
     */
    @Override
    public void statusUpdate(TailwindStatus status) {
        if (status.equals(TailwindStatus.PLAYING) || status.equals(TailwindStatus.RESUMED)) {
            playButton.setIcon(DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_PAUSE_PLAY_ICON),
                PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
        } else if (status.equals(TailwindStatus.PAUSED) || status.equals(TailwindStatus.CLOSED)
            || status.equals(TailwindStatus.END)) {
            playButton.setIcon(DeImage.resizeImage(Global.ico.getFromAsImageIcon(BUTTONCTRL_PLAY_PAUSE_ICON),
                PLAY_PAUSE_ICON_SIZE, PLAY_PAUSE_ICON_SIZE));
        }
    }
}
