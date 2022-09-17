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
