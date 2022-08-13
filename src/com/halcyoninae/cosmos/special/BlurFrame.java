package com.halcyoninae.cosmos.special;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.Dimension;

/**
 * Represents a special component type that features
 * a blurred background. This is primarily targetted
 * towards undecorated frames.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class BlurFrame extends JFrame {
    private JPanel contentPanelDefault;

    public BlurFrame(Dimension size) {
        LayerUI<JComponent> layerUI = new BlurLayer();
        contentPanelDefault = new JPanel();
        contentPanelDefault.setPreferredSize(size);
        JLayer<JComponent> jlayer = new JLayer<>(contentPanelDefault, layerUI);
        add(jlayer);
        setPreferredSize(size);
        setSize(size);
    }
}
