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

import com.jackmeng.halcyoninae.halcyon.constant.ColorManager;
import com.jackmeng.halcyoninae.halcyon.constant.Global;
import com.jackmeng.halcyoninae.halcyon.debug.Debugger;
import com.jackmeng.halcyoninae.halcyon.internal.Localized;

import javax.swing.*;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

@Localized
/**
 * @author Jack Meng
 * @since 3.4.1
 */
public class IconHandler {
    private Map<String, ImageIcon> imageIcons;
    private String defaultLocale;

    /**
     * @param defaultLocale The default lookup location
     */
    public IconHandler(String defaultLocale) {
        imageIcons = new HashMap<>();
        this.defaultLocale = defaultLocale;
        load();
    }

    public void setDefaultLocale(String str) {
        this.defaultLocale = str;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void load() {
        File s = new File(defaultLocale);
        Debugger.warn("[ICO-Handler]: " + s.getAbsolutePath());
        for (String x : s.list(new FilenameFilter() {
            @Override
            public boolean accept(File curr, String str) {
                return new File(curr, str).isDirectory();
            }
        })) {
            Debugger.warn("Scanning Directory: " + new File(x).getAbsolutePath());
            for (File t : new File(x).listFiles()) {
                if (t.getAbsolutePath().endsWith(".png")) {
                    Debugger.warn(defaultLocale + "/" + x + "/" + t.getName());
                    imageIcons.put(defaultLocale + "/" + x + "/" + t.getName(),
                            this.getFromAsImageIcon(defaultLocale + "/" + x + "/" + t.getName()));
                }
            }
        }
    }

    public ImageIcon request(String key) {
        return imageIcons.get(key) == null ? new ImageIcon(new byte[] { (byte) 0xFF, (byte) 0x36 })
                : imageIcons.get(key);
    }

    @Localized(stability = true)
    public ImageIcon requestApplied(String key) {
        return imageIcons.get(key) == null ? new ImageIcon(new byte[] { (byte) 0xFF, (byte) 0x36 })
                : ColorManager.hueTheme(imageIcons.get(key));
    }

    public ImageIcon getFromAsImageIcon(String key) {
        return requestApplied(key);
    }
}
