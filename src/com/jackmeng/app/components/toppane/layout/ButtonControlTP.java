package com.jackmeng.app.components.toppane.layout;

import javax.swing.*;

import com.jackmeng.app.ColorManager;
import com.jackmeng.app.Global;
import com.jackmeng.app.Manager;
import com.jackmeng.app.components.LikeButton;
import com.jackmeng.app.events.AlignSliderWithBar;
import com.jackmeng.app.utils.DeImage;

import java.awt.*;

public class ButtonControlTP extends JPanel {
    private JButton playButton, nextButton, previousButton, loopButton, shuffleButton, mutedButton;
    private LikeButton likeButton;
    private JSlider progressSlider;
    private JProgressBar progressBar;
    private JPanel sliders, buttons;

    public ButtonControlTP() {
        super();
        setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
        setMaximumSize(new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT));
        setMinimumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT));
        setOpaque(false);
        setLayout(new GridLayout(2, 1));

        buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
        buttons.setMinimumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
        buttons.setMaximumSize(new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT / 2));
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, getPreferredSize().height / 6));

        playButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_PLAY_PAUSE_ICON), 40, 40));
        playButton.setBackground(null);
        playButton.setBorder(null);

        nextButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_FWD_ICON), 24, 24));
        nextButton.setBackground(null);
        nextButton.setBorder(null);

        previousButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_BWD_ICON), 24, 24));
        previousButton.setBackground(null);
        previousButton.setBorder(null);

        loopButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LOOP_ICON), 24, 24));
        loopButton.setBackground(null);
        loopButton.setBorder(null);

        shuffleButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_SHUFFLE_ICON), 24, 24));
        shuffleButton.setBackground(null);
        shuffleButton.setBorder(null);

        mutedButton = new JButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_MUTED_ICON), 24, 24));
        mutedButton.setBackground(null);
        mutedButton.setBorder(null);

        likeButton = new LikeButton(
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_NOLIKE_ICON), 24, 24),
                DeImage.resizeImage(Global.rd.getFromAsImageIcon(Manager.BUTTONCTRL_LIKE_ICON), 24, 24));
        likeButton.setBackground(null);
        likeButton.setBorder(null);

        buttons.add(mutedButton);
        buttons.add(shuffleButton);
        buttons.add(previousButton);
        buttons.add(playButton);
        buttons.add(nextButton);
        buttons.add(loopButton);
        buttons.add(likeButton);

        sliders = new JPanel();
        sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
        sliders.setPreferredSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
        sliders.setMinimumSize(new Dimension(Manager.BUTTONCONTROL_MIN_WIDTH, Manager.BUTTONCONTROL_MIN_HEIGHT / 2));
        sliders.setMaximumSize(new Dimension(Manager.BUTTONCONTROL_MAX_WIDTH, Manager.BUTTONCONTROL_MAX_HEIGHT / 2));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(ColorManager.MAIN_FG_THEME);
        progressBar.setBorder(null);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        progressSlider = new JSlider(0, 100);
        progressSlider.setValue(0);
        progressSlider.setForeground(ColorManager.MAIN_FG_THEME);
        progressSlider.setBorder(null);
        progressSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressSlider.addChangeListener(new AlignSliderWithBar(progressSlider, progressBar));

        sliders.add(progressSlider);
        sliders.add(progressBar);

        add(buttons);
        add(sliders);
    }
}
