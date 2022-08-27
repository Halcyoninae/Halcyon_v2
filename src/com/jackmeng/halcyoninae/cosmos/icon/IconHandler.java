/*
 *  Copyright: (C) 2022 MP4J Jack Meng
 * Halcyon MP4J is a music player designed openly..
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

package com.jackmeng.halcyoninae.cosmos.icon;

import com.jackmeng.halcyoninae.cloudspin.CloudSpin;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.filesystem.FileParser;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IconHandler {
    public static String RSCLocale = Manager.RSC_FOLDER_NAME;
    private Color themeColor;
    private Map<String, BufferedImage> icons;
    private WeakReference<String[]> temp;

    public IconHandler(String[] acceptableRuleSets) {
        themeColor = ColorManager.MAIN_FG_THEME;
        this.temp = new WeakReference<>(acceptableRuleSets);
    }

    /**
     * @param locale
     * @param acceptableRuleSets
     * @throws IOException
     */
    private static void load(String locale, String[] acceptableRuleSets) throws IOException {
        Map<String, BufferedImage> map = new HashMap<>();
        if (!FileParser.checkDirExistence(locale)) {
            map = null;
        } else {
            for (File f : Objects.requireNonNull(new File(locale).listFiles((current, name) -> new File(current, name).isDirectory()))) {
                for (File x : Objects.requireNonNull(f.listFiles())) {
                    for (String str : acceptableRuleSets) {
                        if (x.getAbsolutePath().endsWith(str)) {
                            map.put(x.getName(), ImageIO.read(x));
                        }
                    }
                }
            }
        }

    }

    /**
     * @throws IOException
     */
    public void load() throws IOException {
        load(RSCLocale, temp.get());
        temp = null;
    }

    /**
     * @param c
     */
    public void setColorTheme(Color c) {
        this.themeColor = c;
    }


    /**
     * @return Color
     */
    public Color getThemeColor() {
        return themeColor;
    }


    /**
     * @param key
     * @return ImageIcon
     */
    public ImageIcon request(String key) {
        return icons.get(key) == null ? new ImageIcon(CloudSpin.createUnknownIMG())
            : new ImageIcon(CloudSpin.hueImageUnsafe(icons.get(key), ColorTool.colorBreakDown(themeColor)));
    }
}
