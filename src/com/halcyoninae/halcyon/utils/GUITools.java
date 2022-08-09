package com.halcyoninae.halcyon.utils;

import java.awt.Component;
import java.awt.Container;
import java.util.*;

/**
 * Helper tool methods to do with GUI
 *
 * @author Jack Meng
 * @since 3.3
 */
public final class GUITools {
  private GUITools() {}

  /**
   * Get all sub-components of a container;
   * @param c The container to get the components from
   * @return A list of components
   */
  public static List<Component> getAllComponents(Container c) {
    Component[] comps = c.getComponents();
    ArrayList<Component> compList = new ArrayList<>();
    for (Component comp : comps) {
        compList.add(comp);
        if (comp instanceof Container)
            compList.addAll(getAllComponents((Container) comp));
    }
    return compList;
}
}
