package com.halcyoninae.cosmos.theme;

import javax.swing.plaf.basic.BasicLookAndFeel;

import java.awt.Color;

/**
 * A theme is a collection of colors and fonts used to style the UI.
 *
 * This interface is extended by classes providing a "Theme Bundle"
 * which can be called and searched up to find the theme to use.
 *
 * @author Jack Meng
 * @since 3.3
 */
public interface Theme {

  /**
   * Get the class of the main LAF
   *
   * @return the class enumeration of the main LAF
   */
  Class<? extends BasicLookAndFeel> getLAF();

  /**
   * The theme or the background colors
   *
   * This is not to be used as the "main_background" color
   * and instead should be used when the component has a quick
   * comparison with the theme's foreground color.
   *
   * @return the background color
   */
  Color getBackgroundColor();

  /**
   * The theme or the foreground colors
   *
   * @return the foreground color
   */
  Color getForegroundColor();

  /**
   * The theme or the selection colors
   *
   * @return the selection color
   */
  String getThemeName();

  /**
   * Return a simplified version of the theme name.
   * Usually that of the property value to set the theme.
   *
   * @return the theme name
   */
  String getCanonicalName();

  /**
   * Defines what kind of theme this theme is representing based
   * on the ThemeType enum.
   *
   * @return the theme type (enum style)
   */
  ThemeType getThemeType();
}