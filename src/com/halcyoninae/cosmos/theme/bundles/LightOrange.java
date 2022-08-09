package com.halcyoninae.cosmos.theme.bundles;

import java.awt.Color;

import javax.swing.plaf.basic.BasicLookAndFeel;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.halcyoninae.cosmos.theme.Theme;
import com.halcyoninae.cosmos.theme.ThemeType;
import com.halcyoninae.halcyon.utils.ColorTool;

/**
 * This is the default light theme of the program.
 * It features a contrast between OneLight and the native orange look from the
 * DarkOrange color scheme.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class LightOrange implements Theme {

  @Override
  public Class<? extends BasicLookAndFeel> getLAF() {
    return FlatLightFlatIJTheme.class;
  }

  @Override
  public Color getBackgroundColor() {
    return ColorTool.hexToRGBA("#f2c29d");
  }

  @Override
  public Color getForegroundColor() {
    return ColorTool.hexToRGBA("#fa9548");
  }

  @Override
  public String getThemeName() {
    return "Light Orange";
  }

  @Override
  public String getCanonicalName() {
    return "light_orange";
  }

  @Override
  public ThemeType getThemeType() {
    return ThemeType.LIGHT;
  }

}
