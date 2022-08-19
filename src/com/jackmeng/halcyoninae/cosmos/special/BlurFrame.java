/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly.
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

package com.jackmeng.halcyoninae.cosmos.special;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;

/**
 * Represents a special component type that features
 * a blurred background. This is primarily targetted
 * towards undecorated frames.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class BlurFrame extends JFrame {
    private final JPanel contentPanelDefault;

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
