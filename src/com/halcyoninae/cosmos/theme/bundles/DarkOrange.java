package com.halcyoninae.cosmos.theme.bundles;

import java.awt.Color;

import javax.swing.plaf.basic.BasicLookAndFeel;

import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.halcyoninae.cosmos.theme.Theme;
import com.halcyoninae.cosmos.theme.ThemeType;
import com.halcyoninae.halcyon.utils.ColorTool;

/**
 * This is the secondary dark theme of the program.
 * It features a contrast between OneDark and an Orange
 * color.
 *
 * @author Jack Meng
 * @since 3.3
 */
public class DarkOrange implements Theme {

  @Override
  public Class<? extends BasicLookAndFeel> getLAF() {
    return FlatAtomOneDarkIJTheme.class;
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
     return "Dark Orange";
  }

  @Override
  public String getCanonicalName() {
    return "dark_orange";
  }

  @Override
  public ThemeType getThemeType() {
    return ThemeType.DARK;
  }

}
