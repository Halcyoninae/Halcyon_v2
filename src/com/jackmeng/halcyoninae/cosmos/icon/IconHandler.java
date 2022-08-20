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

import java.awt.image.BufferedImage;
import java.awt.Color;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.jackmeng.halcyoninae.cloudspin.CloudSpin;
import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Manager;
import com.jackmeng.halcyoninae.halcyon.filesystem.FileParser;
import com.jackmeng.halcyoninae.halcyon.utils.ColorTool;

public class IconHandler {
    public static String RSCLocale = Manager.RSC_FOLDER_NAME;
    private Color themeColor;
    private Map<String, BufferedImage> icons;
    private String[] temp;

    public IconHandler(String[] acceptableRuleSets) {
        themeColor = ColorManager.MAIN_FG_THEME;
        this.temp = acceptableRuleSets;
    }

    public void load() throws IOException{
        load(RSCLocale, temp);
        temp = null;
    }

    private static Map<String, BufferedImage> load(String locale, String[] acceptableRuleSets) throws IOException {
        Map<String, BufferedImage> map = new HashMap<>();
        if (!FileParser.checkDirExistence(locale)) {
            map = null;
        } else {
            for (File f : new File(locale).listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            })) {
                for (File x : f.listFiles()) {
                    for (String str : acceptableRuleSets) {
                        if (x.getAbsolutePath().endsWith(str)) {
                            map.put(x.getName(), ImageIO.read(x));
                        }
                    }
                }
            }
        }

        return map;
    }

    public void setColorTheme(Color c) {
        this.themeColor = c;
    }

    public Color getThemeColor() {
        return themeColor;
    }

    public ImageIcon request(String key) {
        return icons.get(key) == null ? new ImageIcon(CloudSpin.createUnknownIMG())
                : new ImageIcon(CloudSpin.hueImageUnsafe(icons.get(key), ColorTool.colorBreakDown(themeColor)));
    }
}
