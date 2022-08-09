package com.halcyoninae.cosmos.theme;

import com.halcyoninae.cosmos.theme.bundles.DarkGreen;
import com.halcyoninae.cosmos.theme.bundles.DarkOrange;
import com.halcyoninae.cosmos.theme.bundles.LightGreen;
import com.halcyoninae.cosmos.theme.bundles.LightOrange;

/**
 * A helper class to deal with all the available themes in the bundles.
 *
 * The default theme for the program is the dark green theme.
 *
 * @see com.halcyoninae.cosmos.theme.bundles.DarkGreen
 *
 * @author Jack Meng
 * @since 3.3
 * @see com.halcyoninae.cosmos.theme.Theme
 */
public final class ThemeBundles {
  private ThemeBundles() {
  }

  /**
   * @return The default theme for the program is the dark green theme.
   */
  public static Theme getDefaultTheme() {
    return new DarkGreen();
  }

  /**
   * Get available themes.
   *
   * @return An array representing the available themes.
   */
  public static Theme[] getThemes() {
    return new Theme[] {
        new DarkGreen(),
        new DarkOrange(),
        new LightGreen(),
        new LightOrange()
    };
  }

  /**
   * Search for a theme by its canonical name.
   *
   * @param canonicalName The canonical name of the theme.
   * @return The theme with the given canonical name, or the default if not found.
   */
  public static Theme searchFor(String canonicalName) {
    for (Theme theme : getThemes()) {
      if (theme.getCanonicalName().equals(canonicalName)) {
        return theme;
      }
    }
    return new DarkGreen();
  }

  /**
   * @return All of the available canonical names from the available themes.
   */
  public static String[] getCanonicNames() {
    String[] canonicNames = new String[getThemes().length];
    for (int i = 0; i < getThemes().length; i++)
      canonicNames[i] = getThemes()[i].getCanonicalName();
    return canonicNames;
  }
}
