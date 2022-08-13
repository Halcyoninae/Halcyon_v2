package com.halcyoninae.cosmos.components.bottompane.bbloc.buttons;

import javax.swing.*;

import com.halcyoninae.cosmos.components.bottompane.bbloc.BBlocButton;
import com.halcyoninae.halcyon.constant.Global;
import com.halcyoninae.halcyon.constant.Manager;
import com.halcyoninae.halcyon.utils.DeImage;

import java.awt.event.*;

/**
 * BBloc Entry button to launch the MoreApps
 * Global.moreApps.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class MoreButton extends JButton implements BBlocButton {
    // MoreButton CONFIG START
    public static final String DEFAULT_ICON = Manager.RSC_FOLDER_NAME + "/bbloc/dots.png";
    public static final String PRESSED_ICON = Manager.RSC_FOLDER_NAME + "/bbloc/dots_pressed.png";
    // MoreButton CONFIG END

    private boolean isPressed;

    public MoreButton() {
        setIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(DEFAULT_ICON), 16, 16));
        setRolloverIcon(DeImage.resizeImage(Global.rd.getFromAsImageIcon(PRESSED_ICON), 16, 16));
        setToolTipText("Open a window to display more apps");
        setOpaque(true);
        setBorder(null);
        setDoubleBuffered(true);
        addActionListener(this);
        setBackground(null);
        setContentAreaFilled(false);
        isPressed = false;
        Global.moreApps.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isPressed = false;
            }

            @Override
            public void windowOpened(WindowEvent e) {
                isPressed = true;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                isPressed = false;
            }

            @Override
            public void windowIconified(WindowEvent e) {
                isPressed = false;
            }
        });
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isPressed) {
            Global.moreApps.run();
            isPressed = true;
        } else {
            Global.moreApps.setVisible(false);
            isPressed = false;
        }
    }

}
